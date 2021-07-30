package com.example.san.Service.SrvImp;

import com.example.san.Model.BaseModel.San_Service;
import com.example.san.Model.DAO.IDaoService;
import com.example.san.Service.ISrvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class SrvService implements ISrvService {

    @Autowired
    private IDaoService iDaoService;

    @Override
    public San_Service save(String name, long cost, long capa, Timestamp startTime) {

        Timestamp endTime = this.setServiceDuring(startTime, 12);
        San_Service service = new San_Service(cost, capa, name, startTime, endTime);

        return (San_Service) iDaoService.Save(service);

    }

    @Override
    public Boolean remove(long ServiceId) {
        San_Service service = iDaoService.getById(ServiceId);
        iDaoService.Delete(service);
        return true;
    }

    @Override
    public San_Service edit(long serviceId, String name, long cost, long capa, Timestamp startTime, Timestamp endTime) {
        San_Service oldService = iDaoService.getById(serviceId);
        if (name != null)
            oldService.setServiceName(name);
        if (capa != 0)
            oldService.setCapacity(capa);
        if (cost != 0)
            oldService.setCost(cost);
        if (startTime != null)
            oldService.setStartTime(startTime);
        if (endTime != null)
            oldService.setEndTime(endTime);

        San_Service newService = (San_Service) iDaoService.Update(oldService);
        return newService;
    }

    @Override
    public List<San_Service> getAll(San_Service service) {
        return iDaoService.getAll(service);
    }


    @Override
    public List<San_Service> getAllActiveServices() {
        return iDaoService.getAllActive(Service.class);
    }

    @Override
    public List<San_Service> getAllRelatedServices() {
        return null;
    }

    @Override
    public List<San_Service> getByName(String name) {
        return iDaoService.findByName(name);
    }

    @Override
    public void activateService(long serviceId) {
        San_Service service = iDaoService.getById(serviceId);
        service.setIsActive(true);
        iDaoService.Update(service);
    }

    @Override
    public void deactivateService(long serviceId) {
        San_Service service = iDaoService.getById(serviceId);
        service.setIsActive(false);
        iDaoService.Update(service);
    }

    private Timestamp setServiceDuring(Timestamp startTime, int during) {

        long oldTime=startTime.getTime();
        long t = 12 * 60 * 60 * 60 * 100;
        return new Timestamp(oldTime + t);

    }

}
