package com.example.san.Model.BaseModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "SAN_SERVICE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class San_Service extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    private long id;

    private long Cost;

    private long capacity;

    private String ServiceName;

    private Boolean isActive = false;

    private Timestamp startTime;

    private Timestamp EndTime;

    @JsonIgnore
    @OneToMany(mappedBy = "Service", cascade = CascadeType.REMOVE)
    private List<San_UserService> userServices=new ArrayList<>();;

    @JsonIgnore
    @OneToMany(mappedBy = "service", cascade = CascadeType.REMOVE)
    private List<San_proccess> proccesses=new ArrayList<>();;


    public San_Service(long capacity, long cost, String name, Timestamp startTime, Timestamp endTime) {

        this.capacity = capacity;
        this.Cost = cost;
        this.ServiceName = name;
        this.startTime = startTime;
        this.EndTime = endTime;

    }
}
