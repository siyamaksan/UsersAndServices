package com.example.san.Model.BaseModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "San_User")
@Getter
@Setter
@NoArgsConstructor
public class San_User {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    private long Id;

    private String UserName;

    private String Password;

    @OneToMany(mappedBy = "users")
    private List<San_GroupRole> groupRoles;

    private Boolean isActive;

    private long Credit;

    @OneToMany(mappedBy = "Service",cascade = CascadeType.REMOVE)
    private List<San_UserService> userServices;




}
