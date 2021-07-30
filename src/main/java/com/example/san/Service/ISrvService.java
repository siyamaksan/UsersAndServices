package com.example.san.Service;

import com.example.san.Model.BaseModel.San_Service;
import oracle.sql.TIMESTAMP;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ISrvService {


    San_Service save(String name, long cost,long capa, TIMESTAMP startTime, TIMESTAMP endTime);

    Boolean remove(long serviceId);

    San_Service edit(long serviceId, String name, long cost, long capa, TIMESTAMP startTime, TIMESTAMP endTime);

    List<San_Service> getAll(San_Service service);

    List<San_Service> getAllActiveServices();

    List<San_Service> getByName(String name);

    San_Service activateService(San_Service service);

    San_Service deactivateService(San_Service service);

    San_Service invokeService(San_Service service);


}
