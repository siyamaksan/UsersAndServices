package com.example.san.Model.BaseModel;

import com.example.san.Util.Enums.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Authority")
public class Authority extends BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private Roles Name;

    public Authority(Roles role) {
        this.Name = role;
    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> users;


}
