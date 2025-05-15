package cl.lab.laboratorio.crud.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "USUARIO", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
@SequenceGenerator(name = "usuario_sequence", sequenceName = "usuario_sequence", allocationSize = 1, initialValue = 5)
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "nombres")
    private String nombres;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "password")
    private String password;

    @Column(name = "correo")
    private String correo;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<UsuarioRol> usuarioRoles;

    /**
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return usuarioRoles.stream()
                .map(usuarioRol -> new SimpleGrantedAuthority(usuarioRol.getRol().getDescripcion()))
                .collect(Collectors.toList());
    }

    /**
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
