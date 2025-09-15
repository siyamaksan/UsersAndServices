package com.example.san.repository;

import com.example.san.model.baseModel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository بهینه‌شده برای User با پشتیبانی از Batch Loading
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * پیدا کردن کاربر با نام کاربری - بهینه‌شده
     */
    @Query("SELECT u FROM SAN_USER u WHERE u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

    /**
     * پیدا کردن کاربر با نام کاربری و بارگذاری authorities - بهینه‌شده
     */
    @Query("SELECT u FROM SAN_USER u LEFT JOIN FETCH u.authorities WHERE u.username = :username")
    Optional<User> findByUsernameWithAuthorities(@Param("username") String username);

    /**
     * پیدا کردن کاربر فعال با نام کاربری - بهینه‌شده
     */
    @Query("SELECT u FROM SAN_USER u WHERE u.username = :username AND u.isActive = true")
    Optional<User> findActiveUserByUsername(@Param("username") String username);

    /**
     * پیدا کردن کاربر فعال با نام کاربری و بارگذاری authorities - بهینه‌شده
     */
    @Query("SELECT u FROM SAN_USER u LEFT JOIN FETCH u.authorities WHERE u.username = :username AND u.isActive = true")
    Optional<User> findActiveUserByUsernameWithAuthorities(@Param("username") String username);

    /**
     * بررسی وجود کاربر با نام کاربری - بهینه‌شده
     */
    @Query("SELECT COUNT(u) > 0 FROM SAN_USER u WHERE u.username = :username")
    boolean existsByUsername(@Param("username") String username);

    /**
     * پیدا کردن کاربر با ایمیل - بهینه‌شده
     */
    @Query("SELECT u FROM SAN_USER u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);
}