package com.example.san.Model.BaseModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class San_UserRole {
    @javax.persistence.Id
    @Column(name = "ID")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    private long Id;

    private String RoleName;

    @ManyToOne
    private San_User users;

    @OneToMany(mappedBy = "role")
    private List<San_GroupRole> groupRoles;



}
