package pl.zajaczkowski.bugtracker.auth;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Authority {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    AuthorityName name;

    public Authority(AuthorityName name) {
        this.name = name;
    }
}