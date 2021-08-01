package com.example.san.Service.SrvImp;

import com.example.san.Model.BaseModel.San_Authority;
import com.example.san.Model.Bussiness.ActionResult;
import com.example.san.Model.DAO.IDAOAuthority;
import com.example.san.Model.DAO.IDaoUser;
import com.example.san.Model.BaseModel.San_User;
import com.example.san.Service.ISrvUser;
import com.example.san.Util.Enums.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Set;

@Service
public class SrvUser implements ISrvUser {

    @Autowired
    private IDaoUser iDaoUser;
    @Autowired
    private IDAOAuthority idaoAuthority;

    @Autowired
    private ApplicationContext applicationContext;


    @Override
    public ActionResult Save(String username, String password, Boolean isAdmin) {
        try {
            San_Authority authority = new San_Authority();
            if (isAdmin) {
                authority = (San_Authority) idaoAuthority.getById(1);
            } else {
                authority = (San_Authority) idaoAuthority.getById(2);
            }
            if (iDaoUser.findByUserName(username) != null) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                San_User user = new San_User(username, passwordEncoder.encode(password));
                Set<San_Authority> authoritySet = user.getAuthorities();
                authoritySet.add(authority);
                user.setAuthorities(authoritySet);
                user.getAuthorities().add(new San_Authority(Roles.ROLE_ADMIN));

                return new ActionResult((San_User) iDaoUser.Save(user), 0, "OK");
            } else {
                throw new Exception();
            }
        } catch (
                Exception e) {
            System.out.println(e);
            return new ActionResult(1, "FAIL");

        }


    }

    @Override
    public ActionResult remove(long userId) {
        try {
            San_User user = (San_User) iDaoUser.getById(userId);
            user.setIsActive(false);
            user.setDeleteDateAndTime(new Timestamp(System.currentTimeMillis()));
            iDaoUser.Update(user);
            return new ActionResult(0, "OK");
        } catch (Exception e) {
            System.out.println(e);
            return new ActionResult(1, "FAIL");

        }
    }

    @Override
    public ActionResult edit(long id, String userName, String password) {
        try {
            San_User user = (San_User) iDaoUser.getById(id);
            if (userName != null)
                user.setUsername(userName);
            if (password != null)
                user.setPassword(password);
            user.setLastUpdateDateAndTime(new Timestamp(System.currentTimeMillis()));

            San_User newUser = (San_User) iDaoUser.Update(user);
            return new ActionResult(newUser, 0, "OK");
        } catch (Exception e) {
            System.out.println(e);
            return new ActionResult(1, "FAIL");

        }

    }

    @Override
    public ActionResult getAll() {
        try {
            return new ActionResult(iDaoUser.getAll(San_User.class), 0, "OK");

        } catch (Exception e) {
            System.out.println(e);
            return new ActionResult(1, "FAIL");

        }
    }

    @Override
    public ActionResult getUserByUserName(String userName) {
        try {
            return new ActionResult((San_User) iDaoUser.findByUserName(userName), 0, "OK");

        } catch (Exception e) {
            System.out.println(e);
            return new ActionResult(1, "FAIL");

        }

    }


}
