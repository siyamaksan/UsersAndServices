package com.example.san.Model.BaseModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import oracle.sql.TIMESTAMP;

import javax.persistence.*;
import java.util.List;

@Entity(name = "SERVICE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class San_Service {

    @javax.persistence.Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    private long Id;

    private long Cost;

    private long capacity;

    private String ServiceName;

    private Boolean isActive = false;

    private TIMESTAMP startTime;

    private TIMESTAMP EndTime;

    @OneToMany(mappedBy = "Service", cascade = CascadeType.REMOVE)
    private List<San_UserService> userServices;


    public San_Service(long capacity,long cost, String name, TIMESTAMP startTime, TIMESTAMP endTime) {

        this.capacity=capacity;
        this.Cost = cost;
        this.ServiceName = name;
        this.startTime = startTime;
        this.EndTime = endTime;

    }
}
