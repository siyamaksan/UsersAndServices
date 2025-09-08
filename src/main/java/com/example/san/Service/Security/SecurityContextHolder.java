package com.example.san.Service.Security;

public class SecurityContextHolder {

  static final ThreadLocal<UserModel> userContext = new ThreadLocal<>();

  public static UserModel currentUser() {
    var res = userContext.get();
    if (res == null) {
      res = new UserModel().setUserName("Anonymous").setProfileId(-1L);
      userContext.set(res);
    }
    return res;
  }

}
