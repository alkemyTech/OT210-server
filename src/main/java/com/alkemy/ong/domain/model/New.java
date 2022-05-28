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


@Getter
@Setter
@NoArgsConstructor
@ToString
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE news SET is_active=false WHERE news_id=?")
@Entity
@Table(name = "new")
@EntityListeners(AuditListener.class)
public class New implements Auditable  {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "new_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String image;

    @Embedded
    private Audit audit;

    @ManyToOne
    @JoinColumn(name = "category_id", insertable = false,nullable = false)
    @ToString.Exclude
    private Category categoryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
       New aNew = (New) o;
        return Objects.equals(id, aNew.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
