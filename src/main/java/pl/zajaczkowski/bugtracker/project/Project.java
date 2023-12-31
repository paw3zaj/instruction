package pl.zajaczkowski.bugtracker.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import pl.zajaczkowski.bugtracker.issue.Issue;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@Entity
public class Project {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "project")
    @JsonIgnoreProperties("project")
    private Set<Issue> issues;

    @ColumnDefault(value = "true")
    private Boolean enabled = true;

    @NotEmpty
    private String code;
    private String description;

    @Column(nullable = false)
    private final LocalDate dateCreated = LocalDate.now();

}