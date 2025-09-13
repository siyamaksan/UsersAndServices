package com.example.san.Controller;

import com.example.san.Model.BaseModel.SanService;
import com.example.san.Model.Bussiness.ActionResult;
import com.example.san.Service.ISrvService;
import com.example.san.Service.Security.SecurityContextHolder;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/service")
public class ServiceContoller {

    @Autowired
    private ISrvService srvService;

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/")
    public ActionResult getAllServices() {
        return srvService.getAll();
    }

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/getAllActiveServices", method = RequestMethod.GET)
    public ActionResult getAllActiveServices() {
        return srvService.getAllActiveServices();
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @RequestMapping(value = "/getmyservices", method = RequestMethod.GET)
    public ActionResult getAllRelatedServices(Principal principal) {
        return srvService.getAllRelatedServices(SecurityContextHolder.currentUser().getProfileId());
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public List<SanService> getByName(@RequestParam String name) {
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
    @PostMapping(value = "/")
    public ActionResult createNewService(@RequestParam String name,
                                         @RequestParam long cost,
                                         @RequestParam long capa,
                                         @RequestParam LocalDateTime startTime) {
        return srvService.save(name, cost, capa, startTime);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "/")
    public ActionResult removeService(@RequestParam long serviceId) {
        return srvService.remove(serviceId);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(value = "/")
    public ActionResult updateService(@RequestParam long serviceId,
                                      @RequestParam(required = false) String name,
                                      @RequestParam(required = false) long cost,
                                      @RequestParam(required = false) long capa,
                                      @RequestParam(required = false) LocalDateTime startTime) {


        return srvService.edit(serviceId, name, cost, capa, startTime);
    }


}
