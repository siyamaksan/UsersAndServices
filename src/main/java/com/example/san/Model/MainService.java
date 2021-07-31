package com.example.san.Model;

import com.example.san.Model.BaseModel.San_Service;
import com.example.san.Model.BaseModel.San_User;
import com.example.san.Model.BaseModel.San_UserService;
import com.example.san.Model.DAO.IDaoUserService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
public class MainService extends Thread {


    private San_UserService userService;
    @Autowired
    private IDaoUserService iDaoUserService;


    @Override
    public void run() {


        System.out.println("Main Srvice is running!!!!!!!!!!");
        San_User user = userService.getUser();

        San_Service service = userService.getService();
        long cost = service.getCost();
        user.setCredit(user.getCredit() - cost);
        userService.setCredit(userService.getCredit() - 1);


        iDaoUserService.Save(userService);


    }
}
