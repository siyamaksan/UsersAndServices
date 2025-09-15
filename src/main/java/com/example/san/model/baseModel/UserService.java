package com.example.san.model.baseModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name = "San_UserService")
@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
    name = "San_UserService",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"USER_ID", "SANSERVICE_ID"})
    }
)
public class UserService extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    private long id;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    private User user;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    private Srv srv;

    private long credit;

    public void setLastExecutionTime(LocalDateTime from) {
    }
}
