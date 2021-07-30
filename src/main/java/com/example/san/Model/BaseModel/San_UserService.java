package com.example.san.Model.BaseModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "San_UserService")
@Getter @Setter @NoArgsConstructor
public class San_UserService {

    @javax.persistence.Id
    @Column(name = "ID")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    private long Id;

    @ManyToOne(cascade = CascadeType.MERGE)
    private San_User User;

    @ManyToOne
    private San_Service Service;

    private long Credit;
}
