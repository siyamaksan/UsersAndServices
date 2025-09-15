package com.example.san.model.baseModel;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class BaseEntity implements Serializable {

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "createdBy")
    @BatchSize(size = 20) // Batch loading برای createdBy
    private User createdBy;

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "updateBy")
    @BatchSize(size = 20) // Batch loading برای updateBy
    private User updateBy;

    @Column(name = "createDateAndTime")
    private LocalDateTime createDateAndTime = LocalDateTime.now();

    @Column(name = "lastUpdateDateAndTime")
    private LocalDateTime lastUpdateDateAndTime = LocalDateTime.now();

    @ManyToOne(fetch = jakarta.persistence.FetchType.LAZY)
    @JoinColumn(name = "deletedBy")
    @BatchSize(size = 20) // Batch loading برای deletedBy
    private User deletedBy;

    @Column(name = "deleteDateAndTime")
    private LocalDateTime deleteDateAndTime;
}
