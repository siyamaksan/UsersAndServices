package com.example.san.repository;

import com.example.san.Model.BaseModel.SanProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Repository
@Transactional
public interface ProcessRepository extends JpaRepository<SanProcess,Long> {

    public List<SanProcess> findByUser_id(Long userId);


}
