package com.continuer.Signup_Login.Entites;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import com.continuer.Signup_Login.Enumeraterur.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    @Enumerated(EnumType.STRING)
    
    @Column(nullable = false, unique = true)
    private TypeDeRoles libelle;
}
