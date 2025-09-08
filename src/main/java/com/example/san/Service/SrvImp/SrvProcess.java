package com.example.san.Service.SrvImp;

import com.example.san.Model.BaseModel.SanProcess;
import com.example.san.Model.BaseModel.SanService;
import com.example.san.Model.BaseModel.User;
import com.example.san.Model.BaseModel.UserService;
import com.example.san.Model.Bussiness.ActionResult;
import com.example.san.Service.ISrvProcess;
import com.example.san.Service.MainService;
import com.example.san.repository.ProcessRepository;
import com.example.san.repository.ServiceRepository;
import com.example.san.repository.UserRepository;
import com.example.san.repository.UserServiceRepository;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.transaction.annotation.Transactional;

// Refactored SrvProcess.java
@org.springframework.stereotype.Service
@Transactional
@Slf4j
public class SrvProcess implements ISrvProcess {

  private final UserRepository userDao;
  private final ServiceRepository serviceDao;
  private final UserServiceRepository userServiceDao;
  private final ProcessRepository processDao;
  private final MainService mainService;


  public SrvProcess(UserRepository userDao,
      ServiceRepository serviceDao,
      UserServiceRepository userServiceDao,
      ProcessRepository processDao,
      MainService mainService) {
    this.userDao = userDao;
    this.serviceDao = serviceDao;
    this.userServiceDao = userServiceDao;
    this.processDao = processDao;
    this.mainService = mainService;
  }

  @Override
  @Transactional
  public ActionResult increaseUserCredit(long userId, long amount) {
    if (amount <= 0) {
      return new ActionResult("Amount must be positive");
    }

    try {
      Optional<User> userOptional = userDao.findById(userId);
      if (userOptional.isEmpty()) {
        return new ActionResult("User not found");
      }

      User user = userOptional.get();

      long newCredit = user.getCredit() + amount;
      user.setCredit(newCredit);

      User updatedUser = userDao.save(user);
      return new ActionResult(updatedUser);

    } catch (Exception e) {
      log.error("Error increasing user credit for userId: {}, amount: {}", userId, amount, e);
      throw new ServiceException("Failed to increase user credit", e);
    }
  }


  @Transactional
  @Override
  public ActionResult decreaseUserCredit(long userId, long amount) {
    if (amount <= 0) {
      return new ActionResult("Amount must be positive");
    }

    try {
      User user = userDao.getById(userId);
      if (user == null) {
        return new ActionResult("User not found");
      }

      if (user.getCredit() < amount) {
        return new ActionResult("Insufficient credit");
      }

      user.setCredit(user.getCredit() - amount);
      User updatedUser = userDao.save(user);
      return new ActionResult(updatedUser);

    } catch (Exception e) {
      log.error("Error decreasing user credit for userId: {}, amount: {}", userId, amount, e);
      throw new ServiceException("Failed to decrease user credit", e);
    }
  }

  @Override
  @Transactional
  public ActionResult addUserToService(long userId, long serviceId) {
    try {
      Optional<User> userOptional = userDao.findById(userId);
      if (userOptional.isEmpty()) {
        return new ActionResult("User not found");
      }
      User user = userOptional.get();

      Optional<SanService> sanServiceOptional = serviceDao.findById(serviceId);

      if (sanServiceOptional.isEmpty()) {
        return new ActionResult("Service not found");
      }
      SanService sanService = sanServiceOptional.get();

      // Check if user is already subscribed to this service
      UserService existingUserService = userServiceDao.findByUser_idAndSanService_id(serviceId, userId);
      if (existingUserService != null) {
        return new ActionResult("User is already subscribed to this service");
      }

      UserService userService = UserService.builder()
          .sanService(sanService)
          .user(user)
          .credit(sanService.getCapacity())
          .build();

      UserService savedUserService = userServiceDao.save(userService);
      return new ActionResult(savedUserService);

    } catch (Exception e) {
      log.error("Error adding user {} to service {}", userId, serviceId, e);
      throw new ServiceException("Failed to add user to service", e);
    }
  }

  @Override
  @Transactional
  public ActionResult invokeService(long userId, long serviceId) {
    try {
      UserService userService = userServiceDao.findByUser_idAndSanService_id(serviceId, userId);

      if (userService == null) {
        return new ActionResult("User service subscription not found");
      }

      User user = userService.getUser();
      SanService sanService = userService.getSanService();

      if (user == null || sanService == null) {
        return new ActionResult("Invalid user service data");
      }

      long serviceCost = sanService.getCost();

      if (user.getCredit() < serviceCost) {
        return new ActionResult("Insufficient user credit");
      }

      if (userService.getCredit() <= 0) {
        return new ActionResult("Service usage limit exceeded");
      }

      // Deduct credits
      user.setCredit(user.getCredit() - serviceCost);
      userService.setCredit(userService.getCredit() - 1);

      // Save changes
      userServiceDao.save(userService);

      // Record process history
      recordProcessHistory(user, sanService);

      // Execute service asynchronously
      mainService.executeService(userService);

      return ActionResult.SIMPLE_DONE;

    } catch (Exception e) {
      log.error("Error invoking service {} for user {}", serviceId, userId, e);
      throw new ServiceException("Failed to invoke service", e);
    }
  }

  private void recordProcessHistory(User user, SanService sanService) {
    try {
      SanProcess process = SanProcess.builder()
          .user(user)
          .sanService(sanService)
          .invokeDateAndTime(LocalDateTime.from(Instant.now()))
          .build();

      processDao.save(process);

    } catch (Exception e) {
      log.error("Failed to record process history for user {} and service {}",
          user.getId(), sanService.getId(), e);
      // Don't throw exception here as it's not critical for the main operation
    }
  }

  @Override
  @Transactional(readOnly = true)
  public ActionResult getUserProcessHistory(Long userId) {
    try {
      if (userId == null ) {
        return new ActionResult("Username cannot be empty");
      }

      return new ActionResult(processDao.findByUser_id(userId));

    } catch (Exception e) {
      log.error("Error getting user process history for username: {}", userId, e);
      throw new ServiceException("Failed to get user process history", e);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public ActionResult getAllProcessHistory() {
    try {
      return new ActionResult(processDao.findAll());

    } catch (Exception e) {
      log.error("Error getting all process history", e);
      throw new ServiceException("Failed to get all process history", e);
    }
  }
}