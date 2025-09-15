package com.example.san.service.srvImp;

import com.example.san.enums.UserStatus;
import com.example.san.exception.UserException;
import com.example.san.model.baseModel.SanProcess;
import com.example.san.model.baseModel.Srv;
import com.example.san.model.baseModel.User;
import com.example.san.model.baseModel.UserService;
import com.example.san.model.bussiness.ActionResult;
import com.example.san.repository.ProcessRepository;
import com.example.san.repository.SrvRepository;
import com.example.san.repository.UserRepository;
import com.example.san.repository.UserServiceRepository;
import com.example.san.service.ISrvProcess;
import com.example.san.service.MainService;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Refactored SrvProcess.java
@Service
@Transactional
@Slf4j
public class SrvProcess implements ISrvProcess {

  private final UserRepository userRepository;
  private final SrvRepository srvRepository;
  private final UserServiceRepository userServiceRepository;
  private final ProcessRepository processRepository;
  private final MainService mainService;


  public SrvProcess(UserRepository userRepository, SrvRepository srvRepository,
      UserServiceRepository userServiceDao, ProcessRepository processRepository, MainService mainService,
      UserServiceRepository userServiceRepository) {
    this.userRepository = userRepository;
    this.srvRepository = srvRepository;
    this.userServiceRepository = userServiceDao;
    this.processRepository = processRepository;
    this.mainService = mainService;
  }

  @Override
  @Transactional
  public User increaseUserCredit(long userId, long amount) {

    Optional<User> userOptional = userRepository.findById(userId);
    if (userOptional.isEmpty()) {
      throw new UserException(UserStatus.USER_NOT_FOUND);
    }

    User user = userOptional.get();

    long newCredit = user.getCredit() + amount;
    user.setCredit(newCredit);

    return userRepository.save(user);


  }


  @Transactional
  @Override
  public User decreaseUserCredit(long userId, long amount) {

    Optional<User> userOptional = userRepository.findById(userId);
    if (userOptional.isEmpty()) {
      throw new UserException(UserStatus.USER_NOT_FOUND);
    }
    User user = userOptional.get();

    if (user.getCredit() < amount) {
      throw new UserException(UserStatus.INSUFFICIENT_INVENTORY);
    }

    user.setCredit(user.getCredit() - amount);
    return userRepository.save(user);


  }

  @Override
  @Transactional
  public UserService addUserToService(long userId, long serviceId) {
    Optional<User> userOptional = userRepository.findById(userId);
    if (userOptional.isEmpty()) {
      throw new UserException(UserStatus.USER_NOT_FOUND);
    }

    User user = userOptional.get();

    Optional<Srv> sanServiceOptional = srvRepository.findById(serviceId);

    if (sanServiceOptional.isEmpty()) {
      throw new UserException(UserStatus.USER_NOT_FOUND);
    }
    Srv srv = sanServiceOptional.get();

    // Check if user is already subscribed to this service
    Optional<UserService> existingUserServiceOptional = userServiceRepository.findByUser_idAndSrv_id(
        serviceId, userId);
    if (existingUserServiceOptional.isEmpty()) {
      throw new UserException(UserStatus.USER_NOT_FOUND);
    }

    UserService userService = UserService.builder().srv(srv).user(user).credit(srv.getCapacity())
        .build();

    return userServiceRepository.save(userService);

  }

  @CachePut(value = "processes", key = "#userId")
  @Override
  @Transactional
  public Boolean invokeService(long userId, long serviceId) {

    Optional<UserService> userServiceOptional = userServiceRepository.findByUser_idAndSrv_id(
        serviceId, userId);

    if (userServiceOptional.isEmpty()) {
     throw new UserException(UserStatus.USER_NOT_FOUND);
    }

    UserService userService = userServiceOptional.get();
    User user = userService.getUser();
    Srv srv = userService.getSrv();

    long serviceCost = srv.getCost();

    if (userService.getCredit() <= 0 || user.getCredit() < serviceCost) {
      throw new UserException(UserStatus.INSUFFICIENT_INVENTORY);
    }

    // Deduct credits
    user.setCredit(user.getCredit() - serviceCost);
    userService.setCredit(userService.getCredit() - 1);

    // Save changes
    userServiceRepository.save(userService);

    // Record process history
    recordProcessHistory(user, srv);

    // Execute service asynchronously
     mainService.executeService(userService);

    return true;


  }


  private SanProcess recordProcessHistory(User user, Srv srv) {
    SanProcess process = SanProcess.builder().user(user).srv(srv)
        .invokeDateAndTime(LocalDateTime.from(Instant.now())).build();

    return processRepository.save(process);
  }

  @CachePut(value = "processes", key = "#userId")
  @Override
  @Transactional(readOnly = true)
  public List<SanProcess> getUserProcessHistory(Long userId) {

    return processRepository.findByUser_id(userId);

  }


  @Override
  @Transactional(readOnly = true)
  public ActionResult getAllProcessHistory() {

    return new ActionResult(processRepository.findAll());


  }
}