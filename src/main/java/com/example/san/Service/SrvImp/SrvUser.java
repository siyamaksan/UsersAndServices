package com.example.san.Service.SrvImp;

import com.example.san.Model.BaseModel.San_Service;
import com.example.san.Model.DAO.IDaoService;
import com.example.san.Model.DAO.IDaoUser;
import com.example.san.Model.BaseModel.San_User;
import com.example.san.Service.ISrvUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SrvUser implements ISrvUser {

    @Autowired
    private IDaoUser iDaoUser;


    @Override
    public San_User Save(String username,String password) {
        San_User user=new San_User(username,password);

        return (San_User) iDaoUser.Save(user);

    }

    @Override
    public Boolean remove(long userId) {
        San_User user = iDaoUser.getById(userId);
        iDaoUser.Delete(user);
        return true;
    }

    @Override
    public San_User edit(long id,String userName,String password) {
        San_User user = iDaoUser.getById(id);
        if (userName != null)
            user.setUserName(userName);
        if (password != null)
            user.setPassword(password);

        San_User newUser= (San_User) iDaoUser.Update(user);
        return newUser;    }

    @Override
    public List<San_User> getAll() {
        return iDaoUser.getAll(San_User.class);
    }

    @Override
    public San_User getUserByUserName(String userName) {

        return (San_User) iDaoUser.findByUserName(userName);

    }




}
