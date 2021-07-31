package com.example.san.Service;

import com.example.san.Model.BaseModel.San_Service;
import com.example.san.Model.BaseModel.San_User;
import com.example.san.Model.BaseModel.San_UserService;
import com.example.san.Model.DAO.IDaoUserService;
import com.example.san.Service.SrvImp.SrvProcess;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@Service
public class MainService extends Thread {


    private San_UserService userService;

    @Autowired
    private IDaoUserService iDaoUserService;


    @Override
    public void run() {
        try {
            System.out.println("Main Srvice is running!!!!!!!!!!");
        } catch (Exception e) {

        }


    }


}
