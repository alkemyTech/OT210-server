package com.alkemy.ong.domain.model;

import com.alkemy.ong.domain.model.enumerator.ERole;
import com.alkemy.ong.domain.model.audit.Audit;
import com.alkemy.ong.domain.model.audit.AuditListener;
import com.alkemy.ong.domain.model.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "users")
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE users SET is_active=false WHERE user_id=?")
@EntityListeners(AuditListener.class)
public class Users implements Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "first_name", nullable = false, updatable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false, updatable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "photo")
    private String photo;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = false)
    private ERole role;

    @Embedded
    private Audit audit;
}
