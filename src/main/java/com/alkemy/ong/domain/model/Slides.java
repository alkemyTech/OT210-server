package com.alkemy.ong.domain.model;

import com.alkemy.ong.domain.model.audit.Audit;
import com.alkemy.ong.domain.model.audit.AuditListener;
import com.alkemy.ong.domain.model.audit.Auditable;
import com.amazonaws.services.guardduty.model.Organization;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "slides")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Where(clause = "is_active=true")
@SQLDelete(sql = "UPDATE slides SET is_active=false WHERE slides_id=?")
@EntityListeners(AuditListener.class)
public class Slides implements Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slides_id")
    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    private String text;

    private String order;

    @ManyToOne
    @JoinColumn(name = "organization_id", insertable = false,nullable = false)
    @ToString.Exclude
    private Organization organizationId;

    @Embedded
    private Audit audit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slides slides = (Slides) o;
        return Objects.equals(id, slides.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
