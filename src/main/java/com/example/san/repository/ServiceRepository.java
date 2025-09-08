package com.example.san.repository;


import com.example.san.Model.BaseModel.SanService;
import com.example.san.Model.Bussiness.ActionResult;
import com.example.san.enums.IStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface ServiceRepository extends JpaRepository<SanService, Long> {


    public List<SanService> findByName(String name) ;

    List<SanService> findAllByActive(Boolean active);

    List<SanService> getAllByUserServices_user_id(Long userId);
}
