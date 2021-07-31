package com.example.san.Model.BaseModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "San_proccess")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class San_proccess extends BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    private long id;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    private San_User user;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    private San_Service service;

    private Timestamp invokeDateAndTime;

    private Boolean isSuccess = false;

}
