package com.example.san.Model.BaseModel;

import com.example.san.Util.Enums.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Authority")
@BatchSize(size = 20) // Batch loading برای بهبود عملکرد
public class Authority extends BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private Roles role;

    public Authority(Roles role) {
        this.role = role;
    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @BatchSize(size = 20) // Batch loading برای users
    @Fetch(FetchMode.SUBSELECT) // استفاده از subselect برای بهبود عملکرد
    private Set<User> users;
}
