package com.home.tipster.themes.service;


import com.home.tipster.exception.ConflictException;
import com.home.tipster.themes.model.Themes;
import com.home.tipster.themes.repository.ThemesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThemesServiceImpl {

    private final ThemesRepository themesRepository;


    public Themes create(Themes themes) {
        return saveOrElseThrow(themes);
    }

    private Themes saveOrElseThrow(Themes themes) {
        try {
            return themesRepository.save(themes);
        } catch (DataIntegrityViolationException ex) {
            log.info("Error, title not unique - {}", themes.getTitle());
            throw new ConflictException("This title already exists - " + themes.getTitle());
        }
    }

    public List<Themes> getAll() {
        return themesRepository.findAll().stream()
                .sorted()
                .collect(Collectors.toList());
    }
}
