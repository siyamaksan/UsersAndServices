package com.example.san.Service.Optimized;

import com.example.san.Model.BaseModel.User;
import com.example.san.Model.Bussiness.ActionResult;
import com.example.san.Model.BaseModel.Authority;
import com.example.san.repository.AuthorityRepository;
import com.example.san.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service بهینه‌شده برای User با پشتیبانی از Batch Loading
 */
@Service
@Slf4j
@Transactional(readOnly = true)
public class OptimizedUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * پیدا کردن کاربر با نام کاربری - بهینه‌شده
     */
    public ActionResult getUserByUsername(String username) {
        try {
            Optional<User> userOpt = userRepository.findByUsername(username);
            
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                log.debug("User found: {}", username);
                return new ActionResult(user);
            } else {
                log.warn("User not found: {}", username);
                return ActionResult.SIMPLE_FAILED;
            }
        } catch (Exception e) {
            log.error("Error finding user: {}", e.getMessage(), e);
            return ActionResult.SIMPLE_FAILED;
        }
    }

    /**
     * پیدا کردن کاربر فعال با نام کاربری - بهینه‌شده
     */
    public ActionResult getActiveUserByUsername(String username) {
        try {
            Optional<User> userOpt = userRepository.findActiveUserByUsername(username);
            
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                log.debug("Active user found: {}", username);
                return new ActionResult(user);
            } else {
                log.warn("Active user not found: {}", username);
                return ActionResult.SIMPLE_FAILED;
            }
        } catch (Exception e) {
            log.error("Error finding active user: {}", e.getMessage(), e);
            return ActionResult.SIMPLE_FAILED;
        }
    }

    /**
     * پیدا کردن کاربر با authorities - بهینه‌شده
     */
    public ActionResult getUserByUsernameWithAuthorities(String username) {
        try {
            Optional<User> userOpt = userRepository.findByUsernameWithAuthorities(username);
            
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                log.debug("User with authorities found: {}", username);
                return new ActionResult(user);
            } else {
                log.warn("User with authorities not found: {}", username);
                return ActionResult.SIMPLE_FAILED;
            }
        } catch (Exception e) {
            log.error("Error finding user with authorities: {}", e.getMessage(), e);
            return ActionResult.SIMPLE_FAILED;
        }
    }

    /**
     * ایجاد کاربر جدید - بهینه‌شده
     */
    @Transactional
    public ActionResult createUser(String username, String password, boolean isAdmin) {
        try {
            // بررسی وجود کاربر
            if (userRepository.existsByUsername(username)) {
                log.warn("User already exists: {}", username);
                return ActionResult.SIMPLE_FAILED;
            }

            // ایجاد کاربر
            User user = new User(username, passwordEncoder.encode(password));
            user.setCreateDateAndTime(LocalDateTime.now());
            user.setLastUpdateDateAndTime(LocalDateTime.now());

            // تنظیم نقش
            Authority authority = isAdmin ? 
                authorityRepository.findById(1L).orElse(null) : 
                authorityRepository.findById(2L).orElse(null);
            
            user.getAuthorities().add(authority);

            // ذخیره کاربر
            User savedUser = userRepository.save(user);
            log.info("New user created: {}", username);
            
            return new ActionResult(savedUser);
        } catch (Exception e) {
            log.error("Error creating user: {}", e.getMessage(), e);
            return ActionResult.SIMPLE_FAILED;
        }
    }

    /**
     * به‌روزرسانی کاربر - بهینه‌شده
     */
    @Transactional
    public ActionResult updateUser(Long userId, String username, String password) {
        try {
            Optional<User> userOpt = userRepository.findById(userId);
            
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                
                if (username != null && !username.isEmpty()) {
                    user.setUsername(username);
                }
                
                if (password != null && !password.isEmpty()) {
                    user.setPassword(passwordEncoder.encode(password));
                }
                
                user.setLastUpdateDateAndTime(LocalDateTime.now());
                
                User updatedUser = userRepository.save(user);
                log.info("User updated: {}", userId);
                
                return new ActionResult(updatedUser);
            } else {
                log.warn("User not found for update: {}", userId);
                return ActionResult.SIMPLE_FAILED;
            }
        } catch (Exception e) {
            log.error("Error updating user: {}", e.getMessage(), e);
            return ActionResult.SIMPLE_FAILED;
        }
    }

    /**
     * حذف نرم کاربر - بهینه‌شده
     */
    @Transactional
    public ActionResult softDeleteUser(Long userId) {
        try {
            Optional<User> userOpt = userRepository.findById(userId);
            
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                user.setIsActive(false);
                user.setDeleteDateAndTime(LocalDateTime.now());
                
                User deletedUser = userRepository.save(user);
                log.info("User deleted: {}", userId);
                
                return new ActionResult(deletedUser);
            } else {
                log.warn("User not found for deletion: {}", userId);
                return ActionResult.SIMPLE_FAILED;
            }
        } catch (Exception e) {
            log.error("Error deleting user: {}", e.getMessage(), e);
            return ActionResult.SIMPLE_FAILED;
        }
    }
}
