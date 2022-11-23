package com.home.tipster.tips.model;

import com.home.tipster.themes.model.Themes;
import lombok.*;
import org.hibernate.Hibernate;

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
public class Tips {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "created")
    private LocalDateTime create;

    @Column(name = "updated")
    private LocalDateTime update;

    @ManyToOne
    @JoinColumn(name = "themes", referencedColumnName = "id")
    private Themes themes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Tips tips = (Tips) o;
        return id != null && Objects.equals(id, tips.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
