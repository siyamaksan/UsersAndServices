package com.example.san.Service;

import com.example.san.Model.BaseModel.SanService;
import com.example.san.Model.Bussiness.ActionResult;

import java.time.LocalDateTime;
import java.util.List;

public interface ISrvService {


    ActionResult save(String name, long cost, long capa, LocalDateTime startTime);

    ActionResult remove(long serviceId);

    ActionResult edit(long serviceId, String name, long cost, long capa, LocalDateTime startTime);

    ActionResult getAll();

    ActionResult getAllActiveServices();

    ActionResult getAllRelatedServices(Long  userId);

    List<SanService> getByName(String name);

    ActionResult activateService(long serviceId);

    ActionResult deactivateService(long serviceId);



}
