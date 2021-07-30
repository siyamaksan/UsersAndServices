package com.example.san.Controller;

import com.example.san.Model.BaseModel.San_User;
import com.example.san.Service.ISrvService;
import com.example.san.Service.SrvImp.SrvProcess;
import com.example.san.Service.SrvImp.SrvUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/process")
public class ProcessController {


    @Autowired
    private ISrvService srvService;

    @Autowired
    private SrvUser srvUser;

    @Autowired
    private SrvProcess srvProcess;


    @RequestMapping(value = "/charging", method = RequestMethod.GET)
    public San_User increaseUserCredit(@RequestParam long userId,
                                       @RequestParam long amount    ) {

        return srvProcess.increaseUserCredit(userId,amount);

    }
    @RequestMapping(value = "/decharging", method = RequestMethod.GET)
    public San_User decreaseUserCredite(@RequestParam long userId,
                                        @RequestParam long amount) {

        return srvProcess.decreaseUserCredite(userId,amount);

    }

    @RequestMapping(value = "/invokes")
    public String useService() {
        return "done!!";
    }
}
