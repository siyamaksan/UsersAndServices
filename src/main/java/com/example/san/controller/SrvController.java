package com.example.san.controller;

import com.example.san.model.bussiness.ActionResult;
import com.example.san.service.ISrvService;
import com.example.san.util.SecurityUtils;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/services")
public class SrvController {

  @Autowired
  private ISrvService srvService;


  @Secured("ROLE_ADMIN")
  @GetMapping(value = "/")
  public ActionResult getAllServices() {
    return new ActionResult(srvService.getAll());
  }

  @Secured({"ROLE_ADMIN"})
  @RequestMapping(value = "/getAllActiveServices", method = RequestMethod.GET)
  public ActionResult getAllActiveServices() {
    return new ActionResult(srvService.getAllActiveServices());
  }

  @Secured({"ROLE_ADMIN", "ROLE_USER"})
  @RequestMapping(value = "/getmyservices", method = RequestMethod.GET)
  public ActionResult getAllRelatedServices() {
    return new ActionResult(srvService.getAllRelatedServices(SecurityUtils.getCurrentUserId()));
  }

  @Cacheable(value = "services", key = "#id")
  @Secured("ROLE_ADMIN")
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ActionResult getById(@RequestParam Long id) {
    return new ActionResult(srvService.getById(id));
  }

  @Secured("ROLE_ADMIN")
  @RequestMapping(value = "/search", method = RequestMethod.POST)
  public ActionResult getByName(@RequestParam String name) {
    return new ActionResult(srvService.getByName(name));
  }

  @Secured("ROLE_ADMIN")
  @RequestMapping(value = "/activate", method = RequestMethod.POST)
  public ActionResult activateService(@RequestParam long serviceId) {
    return new ActionResult(srvService.activateService(serviceId));
  }


  @Secured("ROLE_ADMIN")
  @RequestMapping(value = "/deactivate", method = RequestMethod.POST)
  public ActionResult deactivateService(@RequestParam long serviceId) {
    return new ActionResult(srvService.deactivateService(serviceId));
  }

  @Secured("ROLE_ADMIN")
  @PostMapping(value = "/")
  public ActionResult createNewService(@RequestParam String name,
      @RequestParam long cost,
      @RequestParam long capa,
      @RequestParam LocalDateTime startTime) {
    return new ActionResult(srvService.save(name, cost, capa, startTime));
  }


  @Secured("ROLE_ADMIN")
  @DeleteMapping(value = "/")
  public ActionResult removeService(@RequestParam long serviceId) {
    return new ActionResult(srvService.remove(serviceId));
  }


  @Secured("ROLE_ADMIN")
  @PutMapping(value = "/")
  public ActionResult updateService(@RequestParam long serviceId,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) long cost,
      @RequestParam(required = false) long capa,
      @RequestParam(required = false) LocalDateTime startTime) {

    return new ActionResult(srvService.edit(serviceId, name, cost, capa, startTime));
  }


}
