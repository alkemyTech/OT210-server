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

@Entity
@Table(name = "slide")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE slide SET is_active=false WHERE slide_id=?")
@EntityListeners(AuditListener.class)
public class Slide implements Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slide_id")
    private Long id;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    private String text;

    @Column(name = "slide_order", nullable = false)
    private Integer order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    @ToString.Exclude
    private Organization organization;

    @Embedded
    private Audit audit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slide slide = (Slide) o;
        return Objects.equals(id, slide.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
