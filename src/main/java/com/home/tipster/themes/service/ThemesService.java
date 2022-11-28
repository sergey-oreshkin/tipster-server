package com.home.tipster.themes.service;

import com.home.tipster.themes.model.Theme;

import java.util.List;

public interface ThemesService {
    Theme create(Theme theme);

    List<Theme> getAll();
}
