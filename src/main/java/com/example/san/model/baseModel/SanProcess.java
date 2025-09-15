package com.example.san.model.baseModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name = "San_proccess")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanProcess extends BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    private long id;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    private User user;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    private Srv srv;

    private LocalDateTime invokeDateAndTime;

    private Boolean isSuccess = false;

}
