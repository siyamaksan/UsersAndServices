package com.example.san.Model.BaseModel;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity {

    @ManyToOne
    @JoinColumn(name = "createdBy")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "updateBy")
    private User updateBy;

    @Column(name = "createDateAndTime")
    private LocalDateTime createDateAndTime=LocalDateTime.now();


    @Column(name = "lastUpdateDateAndTime")
    private LocalDateTime lastUpdateDateAndTime= LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "deletedBy")
    private User deletedBy;

    @Column(name = "deleteDateAndTime")
    private LocalDateTime deleteDateAndTime;


}
