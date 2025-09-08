package com.example.san.Controller;

import com.example.san.Model.Bussiness.ActionResult;
import com.example.san.Service.SrvImp.SrvProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/process")
public class ProcessController {


    @Autowired
    private SrvProcess srvProcess;

    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/charging", method = RequestMethod.POST)
    public ActionResult increaseUserCredit(@RequestParam long userId,
                                           @RequestParam long amount    ) {

        return srvProcess.increaseUserCredit(userId,amount);

    }
    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/decharging", method = RequestMethod.POST)
    public ActionResult decreaseUserCredite(@RequestParam long userId,
                                        @RequestParam long amount) {

        return srvProcess.decreaseUserCredit(userId,amount);

    }

    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @RequestMapping(value = "/invokes", method = RequestMethod.POST)
    public ActionResult useService(@RequestParam long userId,
                                   @RequestParam long serviceId) {
        return srvProcess.invokeService(userId,serviceId);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/adUserToService", method = RequestMethod.POST)
    public ActionResult adUserToService(@RequestParam long userId,
                                   @RequestParam long serviceId) {
        return srvProcess.addUserToService(userId,serviceId);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ActionResult getAllProcessReport() {
        return srvProcess.getAllProcessHistory();
    }

    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @RequestMapping(value = "/getuserreport", method = RequestMethod.POST)
    public ActionResult getUserProcessReport(@RequestParam long userId) {
        return srvProcess.getUserProcessHistory(userId);
    }
}
