package com.example.san.Service.SrvImp;

import com.example.san.Model.BaseModel.San_Service;
import com.example.san.Model.BaseModel.San_User;
import com.example.san.Model.BaseModel.San_UserService;
import com.example.san.Model.Bussiness.ActionResult;
import com.example.san.Model.DAO.IDaoService;
import com.example.san.Model.DAO.IDaoUser;
import com.example.san.Model.DAO.IDaoUserService;
import com.example.san.Service.ISrvProcess;
import com.example.san.Model.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SrvProcess implements ISrvProcess {

    @Autowired
    private IDaoUser iDaoUser;

    @Autowired
    private IDaoService iDaoService;

    @Autowired
    private IDaoUserService iDaoUserService;

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

            San_UserService userService = new San_UserService();

            userService.setService(service);

            userService.setUser(user);

            return new ActionResult((San_UserService) iDaoUserService.Save(userService), 0, "OK");
        } catch (Exception e) {
            System.out.println(e);
            return new ActionResult(1, "FAIL");

        }


    }

    @Transactional
    public ActionResult invokeService(long serviceId, long userId) {

        try {
            San_UserService userService = iDaoUserService.findByUserAndService(serviceId, userId);
            if (userService.getService() != null || userService.getUser()!=null) {
                MainService mainService = new MainService();
                mainService.setUserService(userService);
                mainService.start();

                return new ActionResult(0, "OK");
            }
            else
                throw new Exception();
        } catch (Exception e) {
            System.out.println(e);
            return new ActionResult(1, "FAIL");

        }


    }

}
