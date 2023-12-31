package pl.zajaczkowski.bugtracker.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.zajaczkowski.bugtracker.auth.interfaces.PersonRepository;

import jakarta.transaction.Transactional;
import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;
    private static final Logger LOG = LoggerFactory.getLogger(CustomUserDetailsService.class);

    public CustomUserDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Optional<Person> optionalPerson = personRepository.findByLogin(login);

        if(optionalPerson.isEmpty()) {
            LOG.info("Login error!!! Attempt to log into the system of a non-existent user: {}", login);
            throw new UsernameNotFoundException(login);
        }
        return buildUserDetails(optionalPerson.get());
    }

    private UserDetails buildUserDetails(Person person) {
        List<GrantedAuthority> authorities = getUserAuthorities(person);
        return new User(person.getLogin(), person.getPassword(), authorities);
    }

    private List<GrantedAuthority> getUserAuthorities(Person person) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Authority authority : person.getAuthorities()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.name.toString()));
        }
        return new ArrayList<>(grantedAuthorities);
    }
}
