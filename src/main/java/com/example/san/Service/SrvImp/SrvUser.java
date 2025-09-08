package com.example.san.Service.SrvImp;

import com.example.san.Model.BaseModel.Authority;
import com.example.san.Model.BaseModel.User;
import com.example.san.Model.Bussiness.ActionResult;
import com.example.san.Service.ISrvUser;
import com.example.san.Util.Enums.Roles;
import com.example.san.repository.AuthorityRepository;
import com.example.san.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SrvUser implements ISrvUser {

  @Autowired
  private UserRepository iUserRepository;
  @Autowired
  private AuthorityRepository idaoAuthorityRepository;

  @Autowired
  private ApplicationContext applicationContext;


  @Override
  public ActionResult save(String username, String password, Boolean isAdmin) {
    try {
      Authority authority = new Authority();
      if (isAdmin) {
        authority = idaoAuthorityRepository.getById(1L);
      } else {
        authority = idaoAuthorityRepository.getById(2L);
      }
      if (iUserRepository.findByUsername(username) != null) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User(username, passwordEncoder.encode(password));
        Set<Authority> authoritySet = user.getAuthorities();
        authoritySet.add(authority);
        user.setAuthorities(authoritySet);
        user.getAuthorities().add(new Authority(Roles.ROLE_ADMIN));

        return new ActionResult(iUserRepository.save(user));
      } else {
        throw new Exception();
      }
    } catch (Exception e) {
      return ActionResult.SIMPLE_FAILED;

    }


  }

  @Override
  public ActionResult remove(long userId) {
    try {
      User user = (User) iUserRepository.getById(userId);
      user.setIsActive(false);
      user.setDeleteDateAndTime( LocalDateTime.now());
      iUserRepository.save(user);
      return ActionResult.SIMPLE_DONE;
    } catch (Exception e) {
      System.out.println(e);
      return  ActionResult.SIMPLE_FAILED;

    }
  }

  @Override
  public ActionResult edit(long id, String userName, String password) {
    try {
      User user = (User) iUserRepository.getById(id);
      if (userName != null) {
        user.setUsername(userName);
      }
      if (password != null) {
        user.setPassword(password);
      }
      user.setLastUpdateDateAndTime(LocalDateTime.now());

      User newUser = (User) iUserRepository.save(user);
      return new ActionResult(newUser);
    } catch (Exception e) {
      System.out.println(e);
      return  ActionResult.SIMPLE_FAILED;

    }

  }

  @Override
  public ActionResult getAll() {
    try {
      return new ActionResult(iUserRepository.findAll());

    } catch (Exception e) {
      System.out.println(e);
      return  ActionResult.SIMPLE_FAILED;

    }
  }

  @Override
  public ActionResult getUserByUserName(String userName) {
    try {
      return new ActionResult((User) iUserRepository.findByUsername(userName));

    } catch (Exception e) {

      return  ActionResult.SIMPLE_FAILED;

    }

  }


}
