package com.example.san.service;

import com.example.san.model.baseModel.Srv;
import java.time.LocalDateTime;
import java.util.List;

public interface ISrvService {


  Srv save(String name, long cost, long capa, LocalDateTime startTime);

  Srv remove(long serviceId);

  Srv edit(Long serviceId, String name, Long cost, Long capa, LocalDateTime startTime);

  List<Srv> getAll();

  List<Srv> getAllActiveServices();

  List<Srv> getAllRelatedServices(Long userId);

  List<Srv> getByName(String name);

  Srv activateService(long serviceId);

  Srv deactivateService(long serviceId);


  Srv getById(Long id);
}
