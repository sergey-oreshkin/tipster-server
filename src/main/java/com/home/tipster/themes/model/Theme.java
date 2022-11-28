package com.home.tipster.themes.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Builder
@Entity
@Table(name = "themes")
@NoArgsConstructor
@AllArgsConstructor
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", unique = true)
    private String title;
    @CreationTimestamp
    @Column(name = "created")
    private LocalDateTime created;
    @UpdateTimestamp
    @Column(name = "updated")
    private LocalDateTime updated;

//    @ManyToOne
//    @JoinColumn(name = "creator")
//    private Users creator;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj)) return false;
        Theme theme = (Theme) obj;
        return id != null && Objects.equals(id, theme.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
