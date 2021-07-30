package com.example.san.Controller;

import com.example.san.Model.BaseModel.San_Service;
import com.example.san.Service.ISrvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping(value = "/service")
public class ServiceContoller {

    @Autowired
    private ISrvService srvService;


    @RequestMapping(value = "/getAll")
    public List<San_Service> getAllServices() {
        return srvService.getAll(new San_Service());
    }

    @RequestMapping(value = "/getAllActiveServices")
    public List<San_Service> getAllActiveServices() {
        return srvService.getAllActiveServices();
    }

    @RequestMapping(value = "/getmyservices")
    public List<San_Service> getAllRelatedServices() {
        return srvService.getAllRelatedServices();
    }

    @RequestMapping(value = "/search")
    public List<San_Service> getByName(@RequestParam String name) {
        return srvService.getByName(name);
    }

    @RequestMapping(value = "/activate")
    public void activateService(@RequestParam long serviceId) {
         srvService.activateService(serviceId);
    }
    @RequestMapping(value = "/deactivate")
    public void deactivateService(@RequestParam long serviceId) {
        srvService.deactivateService(serviceId);
    }

    @RequestMapping(value = "/newservice")
    public void createNewService(@RequestParam String name,
                                   @RequestParam long cost,
                                   @RequestParam long capa,
                                   @RequestParam Timestamp startTime,
                                   @RequestParam Timestamp endTime) {
         srvService.save(name,cost,capa,startTime);
    }

    @RequestMapping(value = "/remove")
    public void removeService(@RequestParam long serviceId) {
         srvService.remove(serviceId);
    }

    @RequestMapping(value = "/update")
    public San_Service updateService(@RequestParam long serviceId,
                                     @RequestParam (required = false) String name,
                                     @RequestParam(required = false) long cost,
                                     @RequestParam(required = false) long capa,
                                     @RequestParam (required = false)Timestamp startTime,
                                     @RequestParam (required = false)Timestamp endTime) {


        return srvService.edit(serviceId,name,cost,capa,startTime,endTime);
    }




}
