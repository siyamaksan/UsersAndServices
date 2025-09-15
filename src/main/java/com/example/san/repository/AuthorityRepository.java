package com.example.san.repository;

import com.example.san.model.baseModel.Authority;
import com.example.san.enums.RolesEnums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuthorityRepository extends JpaRepository<Authority,Long> {


  Authority findByRole(RolesEnums rolesEnums);
}
