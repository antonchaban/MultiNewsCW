package ua.kpi.fict.multinewscw.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.kpi.fict.multinewscw.entities.enums.Role;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString

@Table(name = "customers")
public class Customer implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long customerId;

    private String username;

    @Column(length = 1000)
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "customer_role"
            , joinColumns = @JoinColumn(name = "customer_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    @ToString.Exclude
    @JsonIgnore
    private List<Article> articles;

    // Spring Security

    public boolean isAdmin(){
        return roles.contains(Role.ROLE_ADMIN);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
