package com.example.san.Model.BaseModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "San_User")
@Getter
@Setter
@NoArgsConstructor
public class San_User extends BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    private long Id;
    @Column(unique = true)
    private String UserName;

    private String Password;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.MERGE)
    private List<San_Authority> authorities = new ArrayList<>();

    private Boolean isActive=true;

    private long Credit=0;

    @OneToMany(mappedBy = "Service", cascade = CascadeType.REMOVE)
    private List<San_UserService> userServices = new ArrayList<>();

    public San_User(String userName, String password) {
        this.UserName = userName;
        this.Password = password;


    }
}
