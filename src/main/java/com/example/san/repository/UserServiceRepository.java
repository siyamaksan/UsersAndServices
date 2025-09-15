package com.example.san.repository;

import com.example.san.model.baseModel.UserService;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



@Transactional
@Repository
public interface UserServiceRepository extends JpaRepository<UserService,Long> {


     Optional<UserService> findByUser_idAndSanService_id( long userId,long serviceId) ;


}
