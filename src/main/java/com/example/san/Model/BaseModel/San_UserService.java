package com.example.san.Model.BaseModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "San_UserService")
@Getter @Setter @NoArgsConstructor
public class San_UserService extends BaseEntity {

    @javax.persistence.Id
    @Column(name = "ID")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    private long id;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    private San_User User;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    private San_Service Service;

    private long Credit;
}
