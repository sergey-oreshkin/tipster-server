package com.home.tipster.themes.repository;

import com.home.tipster.themes.model.Themes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemesRepository extends JpaRepository<Themes, Long> {
}
