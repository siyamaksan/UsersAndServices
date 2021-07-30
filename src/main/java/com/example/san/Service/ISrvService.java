package com.example.san.Service;

import com.example.san.Model.BaseModel.San_Service;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

public interface ISrvService {


    San_Service save(String name, long cost,long capa, Timestamp startTime);

    Boolean remove(long serviceId);

    San_Service edit(long serviceId, String name, long cost, long capa, Timestamp startTime, Timestamp endTime);

    List<San_Service> getAll(San_Service service);

    List<San_Service> getAllActiveServices();

    List<San_Service> getAllRelatedServices();


    List<San_Service> getByName(String name);

    void activateService(long serviceId);

    void deactivateService(long serviceId);



}
