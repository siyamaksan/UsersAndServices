package com.example.san.Model.BaseModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "San_User")
@Getter
@Setter
@NoArgsConstructor
public class San_User extends BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    private long id;
    @Column(unique = true)
    private String username;

    private String password;

    @JsonIgnore
    @ManyToMany(mappedBy = "users", cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    private Set<San_Authority> authorities = new HashSet<>();

    private Boolean isActive=true;

    private long Credit=0;

    @JsonIgnore
    @OneToMany(mappedBy = "Service", cascade = CascadeType.REMOVE)
    private List<San_UserService> userServices = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<San_proccess> proccesses = new ArrayList<>();

    public San_User(String userName, String password) {
        this.username = userName;
        this.password = password;


    }
}
