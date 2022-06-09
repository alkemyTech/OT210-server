package com.alkemy.ong.domain.model;


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
import java.util.Objects;
<<<<<<< HEAD
=======
import java.util.Set;

>>>>>>> a7a62cd9c03c981b06157be31b9483b995e64047

@Getter
@Setter
@NoArgsConstructor
@ToString
@Where(clause = "is_active=true")
<<<<<<< HEAD
@SQLDelete(sql = "UPDATE categories SET is_active=false WHERE category_id=?")
=======
@SQLDelete(sql = "UPDATE category SET is_active=false WHERE category_id=?")
>>>>>>> a7a62cd9c03c981b06157be31b9483b995e64047
@Entity
@Table(name = "category")
@EntityListeners(AuditListener.class)
public class Category implements Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private String image;

    @Embedded
    private Audit audit;

<<<<<<< HEAD
=======
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<New> news;

>>>>>>> a7a62cd9c03c981b06157be31b9483b995e64047
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
