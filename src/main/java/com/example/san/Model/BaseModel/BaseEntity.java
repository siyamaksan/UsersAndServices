package com.example.san.Model.BaseModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@MappedSuperclass
public class BaseEntity {

    @ManyToOne
    @JoinColumn(name = "createdBy")
    private San_User createdBy;

    @ManyToOne
    @JoinColumn(name = "updateBy")
    private San_User updateBy;

    @Column(name = "createDateAndTime")
    private Timestamp createDateAndTime=new Timestamp(System.currentTimeMillis());


    @Column(name = "lastUpdateDateAndTime")
    private Timestamp lastUpdateDateAndTime=new Timestamp(System.currentTimeMillis());

    @ManyToOne
    @JoinColumn(name = "deletedBy")
    private San_User deletedBy;

    @Column(name = "deleteDateAndTime")
    private Timestamp deleteDateAndTime;


}
