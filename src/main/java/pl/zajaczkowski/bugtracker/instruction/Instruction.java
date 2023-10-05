package pl.zajaczkowski.bugtracker.instruction;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.Date;

@Setter
@Getter
@Entity
public class Instruction {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String installation;
    private Date createDate;
    private Date modifyDate;
    private String fileType;


}
