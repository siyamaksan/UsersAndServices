package com.example.san.repository;

import com.example.san.model.baseModel.Srv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface SrvRepository extends JpaRepository<Srv, Long> {


    public List<Srv> findByName(String name) ;

    List<Srv> findAllByActive(Boolean active);

    List<Srv> getAllByUserServices_user_id(Long userId);
}
