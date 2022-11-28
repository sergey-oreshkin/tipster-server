package com.home.tipster.themes.service;


import com.home.tipster.exception.ConflictException;
import com.home.tipster.themes.model.Theme;
import com.home.tipster.themes.repository.ThemesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ThemesServiceImpl implements ThemesService {
    private final ThemesRepository themesRepository;

    @Override
    public Theme create(Theme theme) {
        return saveOrElseThrow(theme);
    }

    @Override
    public List<Theme> getAll() {
        return themesRepository.findAll().stream()
                .sorted(Comparator.comparing(Theme::getTitle))
                .collect(Collectors.toList());
    }

    private Theme saveOrElseThrow(Theme theme) {
        try {
            return themesRepository.save(theme);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("This title already exists - " + theme.getTitle());
        }
    }
}
