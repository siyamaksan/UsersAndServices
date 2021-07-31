package com.example.san.Service.SrvImp;

import com.example.san.Model.BaseModel.San_Service;
import com.example.san.Model.BaseModel.San_User;
import com.example.san.Model.BaseModel.San_UserService;
import com.example.san.Model.BaseModel.San_proccess;
import com.example.san.Model.Bussiness.ActionResult;
import com.example.san.Model.DAO.IDaoProcess;
import com.example.san.Model.DAO.IDaoService;
import com.example.san.Model.DAO.IDaoUser;
import com.example.san.Model.DAO.IDaoUserService;
import com.example.san.Service.ISrvProcess;
import com.example.san.Service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
public class SrvProcess implements ISrvProcess {

    @Autowired
    private IDaoUser iDaoUser;

    @Autowired
    private IDaoService iDaoService;

    @Autowired
    private IDaoUserService iDaoUserService;

    @Autowired
    private IDaoProcess iDaoProcess;

    @Override
    public ActionResult increaseUserCredit(long userId, long amount) {
        try {
            San_User user = (San_User) iDaoUser.getById(userId);

            long oldCredit = user.getCredit();
            long newCredit = oldCredit + amount;
            user.setCredit(newCredit);

            return new ActionResult((San_User) iDaoUser.Update(user), 0, "OK");
        } catch (Exception e) {
            System.out.println(e);
            return new ActionResult(1, "FAIL");

        }
    }

    @Override
    public ActionResult decreaseUserCredite(long userId, long amount) {
        try {
            San_User user = (San_User) iDaoUser.getById(userId);

            long oldCredit = user.getCredit();
            if (oldCredit > amount) {
                long newCredit = oldCredit - amount;
                user.setCredit(newCredit);
            }
            return new ActionResult((San_User) iDaoUser.Update(user), 0, "OK");
        } catch (Exception e) {
            System.out.println(e);
            return new ActionResult(1, "FAIL");

        }

    }


    @Override
    public ActionResult addUserToService(long userId, long serviceId) {

        try {
            San_User user = (San_User) iDaoUser.getById(userId);
            San_Service service = (San_Service) iDaoService.getById(serviceId);

            if (user != null && service != null) {

                San_UserService userService = new San_UserService();

                userService.setService(service);

                userService.setUser(user);

                userService.setCredit(service.getCapacity());

                return new ActionResult((San_UserService) iDaoUserService.Save(userService), 0, "OK");
            } else
                throw new Exception();

        } catch (Exception e) {
            System.out.println(e);
            return new ActionResult(1, "FAIL");
        }
    }


    @Transactional
    public ActionResult invokeService(long userId, long serviceId) {

        try {
            San_UserService userService = iDaoUserService.findByUserAndService(serviceId, userId);

            San_User user = userService.getUser();
            San_Service service = userService.getService();
            if (userService.getService() != null || userService.getUser() != null) {

                if (user.getCredit() > service.getCost() && userService.getCredit() > 0) {

                    long cost = service.getCost();
                    user.setCredit(user.getCredit() - cost);
                    userService.setCredit(userService.getCredit() - 1);

                    MainService mainService = new MainService();

                    iDaoUserService.Save(userService);
                    mainService.setUserService(userService);

                    mainService.start();

                    this.setProcessHistory(user, service);
                }


                return new ActionResult(0, "OK");
            } else
                throw new Exception();
        } catch (Exception e) {
            System.out.println(e);
            return new ActionResult(1, "FAIL");

        }

    }

    private void setProcessHistory(San_User user, San_Service service) throws Exception {
        try {
            San_proccess proccess = new San_proccess();

            proccess.setUser(user);
            proccess.setService(service);

            proccess.setInvokeDateAndTime(new Timestamp(System.currentTimeMillis()));


            iDaoProcess.Save(proccess);
        } catch (Exception e) {
            throw new Exception();
        }

    }

    @Override
    public ActionResult getUserProcessHistory(String username) {
        try {

            return new ActionResult(iDaoProcess.getUserProccess(username), 0, "OK");
        } catch (Exception e) {
            return new ActionResult(1, "FAIL");

        }
    }

    @Override
    public ActionResult getAllProcessHistory() {
        try {
            return new ActionResult(iDaoProcess.getAllProccess(), 0, "OK");
        } catch (Exception e) {
            return new ActionResult(1, "FAIL");

        }
    }


}
