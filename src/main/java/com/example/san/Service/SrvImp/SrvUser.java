package com.example.san.Service.SrvImp;

import com.example.san.Controller.Exception.ErrorCode;
import com.example.san.Controller.Exception.UserException;
import com.example.san.Model.BaseModel.Authority;
import com.example.san.Model.BaseModel.User;
import com.example.san.Model.Bussiness.ActionResult;
import com.example.san.Service.ISrvUser;
import com.example.san.Util.Enums.Roles;
import com.example.san.repository.AuthorityRepository;
import com.example.san.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SrvUser implements ISrvUser {

  @Autowired
  private UserRepository iUserRepository;
  @Autowired
  private AuthorityRepository idaoAuthorityRepository;


  @Override
  public ActionResult save(String username, String password, Boolean isAdmin) {
    Authority authority = new Authority();

    authority = idaoAuthorityRepository.findByRole(isAdmin ? Roles.ROLE_ADMIN : Roles.ROLE_USER);

    Optional<User> userOptional = iUserRepository.findByUsername(username);
    if (userOptional.isEmpty()) {
      throw new UserException(ErrorCode.USER_NOT_FOUND);
    }

    User user = userOptional.get();

    user.setPassword(new BCryptPasswordEncoder().encode(password));

    Set<Authority> authoritySet = user.getAuthorities();
    authoritySet.add(authority);
    user.setAuthorities(authoritySet);

    return new ActionResult(iUserRepository.save(user));


  }

  @Override
  public ActionResult remove(long userId) {
    User user = iUserRepository.findById(userId)
        .orElseThrow(() -> new UserException(ErrorCode.USER_NOT_FOUND));

    user.setIsActive(false);

    user.setDeleteDateAndTime(LocalDateTime.now());
    iUserRepository.save(user);

    return ActionResult.SIMPLE_DONE;

  }

  @Override
  public ActionResult edit(long id, String userName, String password) {

    Optional<User> userOptional = iUserRepository.findById(id);
    if (userOptional.isEmpty()) {
      throw new UserException(ErrorCode.USER_NOT_FOUND);
    }
    User user = userOptional.get();
    user.setUsername(userName);
    user.setPassword(password);
    user.setLastUpdateDateAndTime(LocalDateTime.now());

    User newUser = iUserRepository.save(user);
    return new ActionResult(newUser);


  }

  @Override
  public ActionResult getAll() {
    return new ActionResult(iUserRepository.findAll());
  }

  @Override
  public ActionResult getUserByUserName(String userName) {
    return iUserRepository.findByUsername(userName)
        .map(ActionResult::new)
        .orElse(new ActionResult(ErrorCode.USER_NOT_FOUND));
  }

}
