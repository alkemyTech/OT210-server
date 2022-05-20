package com.alkemy.ong.domain.model;


import com.alkemy.ong.domain.model.audit.Audit;
import com.alkemy.ong.domain.model.audit.AuditListener;
import com.alkemy.ong.domain.model.audit.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE categories SET is_active=false WHERE category_id=?")
@Entity
@Table(name = "categories")
@EntityListeners(AuditListener.class)

public class Category implements Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column()
    private String description;

    @Column
    private String image;

    @Column(name = "date_of_creation", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOfCreation;

    @Column(name = "modification_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modificationDate;

    @OneToMany(mappedBy = "categoryId")
    @ToString.Exclude
    private Set<New> aNew;

    @Embedded
    private Audit audit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
       Category category = (Category) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }






}
