package com.home.tipster.themes.repository;

import com.home.tipster.themes.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemesRepository extends JpaRepository<Theme, Long> {
}
