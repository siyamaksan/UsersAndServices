package com.example.san.Service;

import com.example.san.Model.BaseModel.San_Service;
import com.example.san.Model.Bussiness.ActionResult;

import java.sql.Timestamp;
import java.util.List;

public interface ISrvService {


    ActionResult save(String name, long cost, long capa, Timestamp startTime);

    ActionResult remove(long serviceId);

    ActionResult edit(long serviceId, String name, long cost, long capa, Timestamp startTime);

    ActionResult getAll(San_Service service);

    ActionResult getAllActiveServices();

    ActionResult getAllRelatedServices(String  userId);


    List<San_Service> getByName(String name);

    ActionResult activateService(long serviceId);

    ActionResult deactivateService(long serviceId);



}
