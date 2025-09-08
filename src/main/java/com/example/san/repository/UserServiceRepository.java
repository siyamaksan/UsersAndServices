package com.example.san.repository;

import com.example.san.Model.BaseModel.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



@Transactional
@Repository
public interface UserServiceRepository extends JpaRepository<UserService,Long> {


    public UserService findByUser_idAndSanService_id( long userId,long serviceId) ;


}
