package com.example.san.Service.SrvImp;

import com.example.san.Controller.Exception.UserException;
import com.example.san.enums.UserStatus;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Refactored SrvProcess.java
@Service
@Transactional
@Slf4j
public class SrvProcess implements ISrvProcess {

  private final UserRepository userDao;
  private final ServiceRepository serviceRepository;
  private final UserServiceRepository userServiceRepository;
  private final ProcessRepository processDao;
  private final MainService mainService;


  public SrvProcess(UserRepository userDao,
      ServiceRepository serviceRepository,
      UserServiceRepository userServiceDao,
      ProcessRepository processDao,
      MainService mainService, UserServiceRepository userServiceRepository) {
    this.userDao = userDao;
    this.serviceRepository = serviceRepository;
    this.userServiceRepository = userServiceDao;
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

      Optional<SanService> sanServiceOptional = serviceRepository.findById(serviceId);

      if (sanServiceOptional.isEmpty()) {
        throw new UserException(UserStatus.USER_NOT_FOUND);
      }
      SanService sanService = sanServiceOptional.get();

      // Check if user is already subscribed to this service
      Optional<UserService> existingUserServiceOptional = userServiceRepository.findByUser_idAndSanService_id(
          serviceId, userId);
      if (existingUserServiceOptional.isEmpty()) {
        return new ActionResult("User is already subscribed to this service");
      }

      UserService userService = UserService.builder()
          .sanService(sanService)
          .user(user)
          .credit(sanService.getCapacity())
          .build();

      UserService savedUserService = userServiceRepository.save(userService);
      return new ActionResult(savedUserService);

    } catch (Exception e) {
      log.error("Error adding user {} to service {}", userId, serviceId, e);
      throw new ServiceException("Failed to add user to service", e);
    }
  }

  @Override
  @Transactional
  public ActionResult invokeService(long userId, long serviceId) {

    Optional<UserService> userServiceOptional = userServiceRepository.findByUser_idAndSanService_id(
        serviceId, userId);

    if (userServiceOptional.isEmpty()) {
      return new ActionResult("User service subscription not found");
    }

    UserService userService = userServiceOptional.get();
    User user = userService.getUser();
    SanService sanService = userService.getSanService();

    long serviceCost = sanService.getCost();

    if (userService.getCredit() <= 0 || user.getCredit() < serviceCost) {
      throw new UserException(UserStatus.INSUFFICIENT_INVENTORY);
    }

    // Deduct credits
    user.setCredit(user.getCredit() - serviceCost);
    userService.setCredit(userService.getCredit() - 1);

    // Save changes
    userServiceRepository.save(userService);

    // Record process history
    recordProcessHistory(user, sanService);

    // Execute service asynchronously
    mainService.executeService(userService);

    return ActionResult.SIMPLE_DONE;


  }

  private void recordProcessHistory(User user, SanService sanService) {
    SanProcess process = SanProcess.builder()
        .user(user)
        .sanService(sanService)
        .invokeDateAndTime(LocalDateTime.from(Instant.now()))
        .build();

    processDao.save(process);
  }

  @Override
  @Transactional(readOnly = true)
  public ActionResult getUserProcessHistory(Long userId) {

    return new ActionResult(processDao.findByUser_id(userId));

  }

  @Override
  @Transactional(readOnly = true)
  public ActionResult getAllProcessHistory() {

    return new ActionResult(processDao.findAll());


  }
}