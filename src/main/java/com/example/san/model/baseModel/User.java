package com.example.san.model.baseModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "SAN_USER")
@Getter
@Setter
@NoArgsConstructor
@BatchSize(size = 20) // Batch loading برای بهبود عملکرد
public class User extends BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    private long id;
    @Column(unique = true)
    private String username;

    private String password;

    @JsonIgnore
    @ManyToMany(mappedBy = "users", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @BatchSize(size = 10) // Batch loading برای authorities
    @Fetch(FetchMode.SUBSELECT) // استفاده از subselect برای بهبود عملکرد
    private Set<Authority> authorities = new HashSet<>();

    private Boolean isActive=true;

    private long Credit=0;

    private String email;

    private Boolean accountNonExpired;


    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @BatchSize(size = 20) // Batch loading برای userServices
    private List<UserService> userServices = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @BatchSize(size = 20) // Batch loading برای processes
    private List<SanProcess> processes = new ArrayList<>();

    public User(String userName, String password) {
        this.username = userName;
        this.password = password;


    }
}
