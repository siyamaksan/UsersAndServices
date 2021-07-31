package com.example.san.Service.SrvImp;

import com.example.san.Model.BaseModel.San_Service;
import com.example.san.Model.BaseModel.San_UserService;
import com.example.san.Model.Bussiness.ActionResult;
import com.example.san.Model.DAO.IDaoService;
import com.example.san.Service.ISrvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class SrvService implements ISrvService {

    @Autowired
    private IDaoService iDaoService;

    @Override
    public ActionResult save(String name, long cost, long capa, Timestamp startTime) {
        try {
            Timestamp endTime = this.setServiceDuring(startTime, 12);
            San_Service service = new San_Service(cost, capa, name, startTime, endTime);

            return new ActionResult((San_Service) iDaoService.Save(service), 0, "OK");
        } catch (Exception e) {
            System.out.println(e);
            return new ActionResult(1, "FAIL");

        }
    }

    @Override
    public ActionResult remove(long ServiceId) {

        try {
            San_Service service = (San_Service) iDaoService.getById(ServiceId);
            service.setIsActive(false);
            service.setDeleteDateAndTime(new Timestamp(System.currentTimeMillis()));
            iDaoService.Update(service);
            return new ActionResult(0, "OK");
        } catch (Exception e) {
            System.out.println(e);
            return new ActionResult(1, "FAIL");

        }

    }

    @Override
    public ActionResult edit(long serviceId, String name, long cost, long capa, Timestamp startTime) {
        try {
            San_Service oldService = (San_Service) iDaoService.getById(serviceId);
            if (name != null)
                oldService.setServiceName(name);
            if (capa != 0)
                oldService.setCapacity(capa);
            if (cost != 0)
                oldService.setCost(cost);
            if (startTime != null)
                oldService.setStartTime(startTime);


            oldService.setLastUpdateDateAndTime(new Timestamp(System.currentTimeMillis()));

            San_Service newService = (San_Service) iDaoService.Update(oldService);
            return new ActionResult(newService, 0, "OK");
        } catch (Exception e) {
            System.out.println(e);
            return new ActionResult(1, "FAIL");

        }


    }

    @Override
    public ActionResult getAll(San_Service service) {

        try {
            return new ActionResult(iDaoService.getAll(service), 0, "OK");

        } catch (Exception e) {
            System.out.println(e);
            return new ActionResult(1, "FAIL");

        }
    }


    @Override
    public ActionResult getAllActiveServices() {
        try {
            return new ActionResult(iDaoService.getAllActive(Service.class), 0, "OK");

        } catch (Exception e) {
            System.out.println(e);
            return new ActionResult(1, "FAIL");

        }
    }

    @Override
    public ActionResult getAllRelatedServices(long userId) {
        try {

            List<San_UserService> userService= (List<San_UserService>) iDaoService.getUserService(userId);
            List<San_Service> services=new ArrayList<>();
            for (San_UserService us:userService  ) {
                 services.add(us.getService());

            }
            return new ActionResult(services,0, "OK");
        } catch (Exception e) {
            System.out.println(e);
            return new ActionResult(1, "FAIL");

        }
    }

    @Override
    public List<San_Service> getByName(String name) {
        return iDaoService.findByName(name);
    }

    @Override
    public ActionResult activateService(long serviceId) {

        try {
            San_Service service = (San_Service) iDaoService.getById(serviceId);
            service.setIsActive(true);

            return new ActionResult(iDaoService.Update(service), 0, "OK");
        } catch (Exception e) {
            System.out.println(e);
            return new ActionResult(1, "FAIL");
        }
    }

    @Override
    public ActionResult deactivateService(long serviceId) {
        try {
            San_Service service = (San_Service) iDaoService.getById(serviceId);
            service.setIsActive(false);
            return new ActionResult(iDaoService.Update(service),0,"OK");
        }catch (Exception e) {
            System.out.println(e);
            return new ActionResult(1, "FAIL");

        }

    }

    private Timestamp setServiceDuring(Timestamp startTime, int during) {

        long oldTime = startTime.getTime();
        long t = 12  * 60 * 60 * 1000;

        return new Timestamp(oldTime + t);

    }

}
