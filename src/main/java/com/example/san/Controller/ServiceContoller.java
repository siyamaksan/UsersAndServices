package com.example.san.Controller;

import com.example.san.Model.BaseModel.San_Service;
import com.example.san.Model.Bussiness.ActionResult;
import com.example.san.Service.ISrvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping(value = "/service")
public class ServiceContoller {

    @Autowired
    private ISrvService srvService;

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ActionResult getAllServices() {
        return srvService.getAll(new San_Service());
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/getAllActiveServices", method = RequestMethod.GET)
    public ActionResult getAllActiveServices() {
        return srvService.getAllActiveServices();
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping(value = "/getmyservices", method = RequestMethod.GET)
    public ActionResult getAllRelatedServices(Principal principal) {
        return srvService.getAllRelatedServices(principal.getName());
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public List<San_Service> getByName(@RequestParam String name) {
        return srvService.getByName(name);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/activate", method = RequestMethod.POST)
    public ActionResult activateService(@RequestParam long serviceId) {
        return srvService.activateService(serviceId);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/deactivate", method = RequestMethod.POST)
    public ActionResult deactivateService(@RequestParam long serviceId) {
        return srvService.deactivateService(serviceId);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/newservice", method = RequestMethod.POST)
    public ActionResult createNewService(@RequestParam String name,
                                         @RequestParam long cost,
                                         @RequestParam long capa,
                                         @RequestParam Timestamp startTime) {
        return srvService.save(name, cost, capa, startTime);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public ActionResult removeService(@RequestParam long serviceId) {
        return srvService.remove(serviceId);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ActionResult updateService(@RequestParam long serviceId,
                                      @RequestParam(required = false) String name,
                                      @RequestParam(required = false) long cost,
                                      @RequestParam(required = false) long capa,
                                      @RequestParam(required = false) Timestamp startTime) {


        return srvService.edit(serviceId, name, cost, capa, startTime);
    }


}
