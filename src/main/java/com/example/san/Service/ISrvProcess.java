package com.example.san.Service;

import com.example.san.Model.BaseModel.San_User;
import com.example.san.Model.BaseModel.San_UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public interface ISrvProcess {


    San_User increaseUserCredit(long userId,long amount) ;

    San_User decreaseUserCredite(long userId,long amount);

    String invikeService();

    San_UserService addUserToService(long userId,long SeviceId);


}
