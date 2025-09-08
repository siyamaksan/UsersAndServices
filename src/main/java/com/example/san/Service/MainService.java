package com.example.san.Service;

import com.example.san.Model.BaseModel.SanService;
import com.example.san.Model.BaseModel.User;
import com.example.san.Model.BaseModel.UserService;
import com.example.san.repository.UserServiceRepository;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;


// Refactored MainService.java
@org.springframework.stereotype.Service
@Slf4j
public class MainService {

    private final UserServiceRepository userServiceDao;
    private final TaskExecutor taskExecutor;

    public MainService(UserServiceRepository userServiceDao,
        @Qualifier("serviceExecutor") TaskExecutor taskExecutor) {
        this.userServiceDao = userServiceDao;
        this.taskExecutor = taskExecutor;
    }

    @Async("serviceExecutor")
    public CompletableFuture<Void> executeService(UserService userService) {
        return CompletableFuture.runAsync(() -> {
            try {
                log.info("Executing service {} for user {}",
                    userService.getSanService().getId(),
                    userService.getUser().getId());

                // Your actual service logic goes here
                processService(userService);

                log.info("Service execution completed successfully for user {}",
                    userService.getUser().getId());

            } catch (Exception e) {
                log.error("Error executing service for user {}",
                    userService.getUser().getId(), e);
                // Handle service execution failure
                handleServiceFailure(userService, e);
            }
        }, taskExecutor);
    }

    private void processService(UserService userService) {
        // Implement your actual service processing logic here
        // This replaces the simple println from the original code

        try {
            // Simulate some processing time
            Thread.sleep(1000);

            // Update service execution status if needed
            userService.setLastExecutionTime(LocalDateTime.from(Instant.now()));
            userServiceDao.save(userService);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ServiceException("Service execution was interrupted", e);
        }
    }

    private void handleServiceFailure(UserService userService, Exception e) {
        try {
            // Rollback credits if service execution fails
            User user = userService.getUser();
            SanService sanService = userService.getSanService();

            // Restore credits
            user.setCredit(user.getCredit() + sanService.getCost());
            userService.setCredit(userService.getCredit() + 1);

            userServiceDao.save(userService);

            log.info("Credits restored for user {} due to service execution failure",
                user.getId());

        } catch (Exception rollbackException) {
            log.error("Failed to rollback credits for user {}",
                userService.getUser().getId(), rollbackException);
        }
    }
}
