package org.example.u5w3d5compito.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.example.u5w3d5compito.entities.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "utenti")
@JsonIgnoreProperties({"password", "ruolo"})
@NoArgsConstructor
public class Utente implements UserDetails {
    @Id
    @GeneratedValue
    @Column(name = "utente_id")
    @Setter(AccessLevel.NONE)
    private UUID utenteId;
    @Column
    private String nome;
    @Column
    private String cognome;
    @Column
    private LocalDate dataNascita;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role ruolo;

    public Utente(String nome, String cognome, LocalDate dataNascita, String email, String password, Role ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataNascita = dataNascita;
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.ruolo.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }


}