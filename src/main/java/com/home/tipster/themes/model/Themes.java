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
public class Themes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    private String title;
    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updated;

//    @ManyToOne
//    @JoinColumn(name = "creator")
//    private Users creator;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || Hibernate.getClass(this) != Hibernate.getClass(obj)) return false;
        Themes themes = (Themes) obj;
        return id != null && Objects.equals(id, themes.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
