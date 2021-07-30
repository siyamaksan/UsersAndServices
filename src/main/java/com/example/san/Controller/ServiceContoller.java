package com.example.san.Controller;

import com.example.san.Model.BaseModel.San_Service;
import com.example.san.Service.ISrvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/getmyservices")
    public String getAllRelatedServices() {
        return "done!!";
    }

    @RequestMapping(value = "/activate")
    public String activateService() {
        return "done!!";
    }

    @RequestMapping(value = "/newservice")
    public String createNewService() {
        return "done!!";
    }

    @RequestMapping(value = "/invokes")
    public String useService() {
        return "done!!";
    }

}
