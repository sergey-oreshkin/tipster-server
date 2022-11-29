package com.home.tipster.themes.factory;

import com.home.tipster.exception.NotFoundException;
import com.home.tipster.themes.model.Theme;
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

    public Theme getTheme(Long id) throws NotFoundException {
        return themesRepository.findById(id).orElseThrow(
                () -> new NotFoundException(
                        String.format("The theme with id = %d wasn't found", id)));
    }
}
