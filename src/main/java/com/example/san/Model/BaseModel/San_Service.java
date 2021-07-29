package com.example.san.Model.BaseModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import oracle.sql.TIMESTAMP;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class San_Service {

    @javax.persistence.Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    private long Id;

    private long Cost;

    private long ServiceName;

    private Boolean isActive;

    private TIMESTAMP startTime;

    private TIMESTAMP EndTime;

    @OneToMany(mappedBy = "Service",cascade = CascadeType.REMOVE)
    private List<San_UserService> userServices;


}
