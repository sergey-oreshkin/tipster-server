package com.home.tipster.tips.model;

import com.home.tipster.themes.model.Themes;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Andrey Boyarov
 */
@Getter
@Setter
@Entity
@Builder
@ToString
@Table(name = "tips")
@NoArgsConstructor
@AllArgsConstructor
public class Tip {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "created")
    @CreationTimestamp
    private LocalDateTime create;

    @Column(name = "updated")
    @UpdateTimestamp
    private LocalDateTime update;

    @ManyToOne
    @JoinColumn(name = "themes", referencedColumnName = "id")
    private Themes theme;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Tip tip = (Tip) o;
        return id != null && Objects.equals(id, tip.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
