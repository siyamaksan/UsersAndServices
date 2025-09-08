package com.example.san.repository;

import com.example.san.Model.BaseModel.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuthorityRepository extends JpaRepository<Authority,Long> {


}
