package com.example.san.Service.Security;


import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.tomcat.util.collections.CaseInsensitiveKeyMap;

@Data
@Getter
@Setter
@Accessors(chain = true)
public class UserModel {

  public UserModel() {
    this.userName = "Anonymous";
    this.roles = new CaseInsensitiveKeyMap<>();
    this.permissions = new HashSet<>();
    this.claims = new CaseInsensitiveKeyMap<>();
    this.scopes = new HashSet<>();
  }

  private String id;
  private String userName;
  private String phoneNumber;
  private String nationalCode;
  private Long profileId;
  private String channel;

  private final Map<String, Set<String>> roles;
  private final HashSet<String> permissions;
  private final Set<String> scopes;
  protected final Map<String, Object> claims;



}
