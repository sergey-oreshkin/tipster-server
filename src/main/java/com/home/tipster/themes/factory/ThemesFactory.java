package com.home.tipster.themes.factory;

import com.home.tipster.themes.model.Themes;
import com.home.tipster.themes.repository.ThemesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Andrey Boyarov
 */

@Component
@RequiredArgsConstructor
public class ThemesFactory {

    private final ThemesRepository themesRepository;

    public Themes getTheme(Long id){
       return themesRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}
