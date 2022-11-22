package com.home.tipster.themes.service;


import com.home.tipster.themes.model.Themes;
import com.home.tipster.themes.repository.ThemesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ThemesServiceImpl {

    private final ThemesRepository themesRepository;


    public Themes create(Themes themes) {
        return saveOrElseThrow(themes);
    }

    private Themes saveOrElseThrow(Themes themes) {
        return themesRepository.save(themes);

//        try {
//            return themesRepository.save(themes);
//        } catch (DataIntegrityViolationException ex) {
//            throw new ConflictException("Themes title already in use", "title = " + themes.getTitle());
//        }
    }

    public List<Themes> getAll() {
        return themesRepository.findAll();
    }
}
