package com.example.san.Service.SrvImp;

import com.example.san.Model.BaseModel.San_Service;
import com.example.san.Model.BaseModel.San_User;
import com.example.san.Model.BaseModel.San_UserService;
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
    public San_User increaseUserCredit(long userId, long amount) {
        San_User user = iDaoUser.getById(userId);

        long oldCredit = user.getCredit();
        long newCredit = oldCredit + amount;
        user.setCredit(newCredit);
        return (San_User) iDaoUser.Update(user);

    }

    @Override
    public San_User decreaseUserCredite(long userId, long amount) {
        San_User user = iDaoUser.getById(userId);

        long oldCredit = user.getCredit();
        if (oldCredit > amount) {
            long newCredit = oldCredit - amount;
            user.setCredit(newCredit);
        }
        return (San_User) iDaoUser.Update(user);
    }


    @Override
    public San_UserService addUserToService(long userId, long serviceId) {

        San_User user = iDaoUser.getById(userId);

        San_Service service = iDaoService.getById(serviceId);

        San_UserService userService = new San_UserService();

        userService.setService(service);

        userService.setUser(user);

        return (San_UserService) iDaoUserService.Save(userService);
    }

    @Transactional
    public void invokeService(long serviceId, long userId) {

        San_UserService userService = iDaoUserService.findByUserAndService(serviceId, userId);

        MainService mainService = new MainService();
        mainService.setUserService(userService);
        mainService.start();

    }

}
