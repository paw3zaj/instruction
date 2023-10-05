package pl.zajaczkowski.bugtracker.issue;

import lombok.Getter;
import lombok.Setter;
import pl.zajaczkowski.bugtracker.auth.Person;
import pl.zajaczkowski.bugtracker.project.Project;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Entity
public class Issue {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Size(max = 100)
    @Column(nullable = false, unique = true, length = 100)
    private String name;

    private String description;

    @NotEmpty
    @Size(max = 20)
    @Column(unique = true, length = 20)
    private String code;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @OneToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private Person creator;

    @NotNull
    @OneToOne
    @JoinColumn(name = "assignee_id", nullable = false)
    private Person assignee;

    @Column(nullable = false)
    private final LocalDate dateCreated = LocalDate.now();

    private LocalDate lastUpdate;
    private Double estimatedTime;

    @OneToMany(mappedBy = "issue")
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "priority_id")
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;
}