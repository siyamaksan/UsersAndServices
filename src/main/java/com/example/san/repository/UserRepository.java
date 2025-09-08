package com.example.san.repository;

import com.example.san.Model.BaseModel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  public User findByUsername(String UserName);


}
