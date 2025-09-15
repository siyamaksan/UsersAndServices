package com.example.san.model.baseModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "SAN_SERVICE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Srv extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    private long id;

    private long Cost;

    private long capacity;

    private String name;

    private Boolean active = false;

    private LocalDateTime startTime;

    private LocalDateTime EndTime;

    @JsonIgnore
    @OneToMany(mappedBy = "srv", cascade = CascadeType.REMOVE)
    private List<UserService> userServices=new ArrayList<>();;

    @JsonIgnore
    @OneToMany(mappedBy = "srv", cascade = CascadeType.REMOVE)
    private List<SanProcess> processes=new ArrayList<>();;


    public Srv(long capacity, long cost, String name, LocalDateTime startTime, LocalDateTime endTime) {

        this.capacity = capacity;
        this.Cost = cost;
        this.name = name;
        this.startTime = startTime;
        this.EndTime = endTime;

    }
}
