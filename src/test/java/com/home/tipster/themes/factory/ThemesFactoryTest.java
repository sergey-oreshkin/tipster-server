package com.home.tipster.themes.factory;

import com.home.tipster.exception.NotFoundException;
import com.home.tipster.themes.model.Theme;
import com.home.tipster.themes.repository.ThemesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


/**
 * @author Andrey Boyarov
 */
@ExtendWith(MockitoExtension.class)
class ThemesFactoryTest {

    private static final Long BASE_THEME_ID = 22L;

    public static final String BASE_THEME_TITLE = "Theme title";

    public static final String NOT_FOUND_MESSAGE = "not found";

    public static final Theme BASE_THEME = Theme.builder()
            .id(BASE_THEME_ID)
            .title(BASE_THEME_TITLE)
            .build();

    @InjectMocks
    private ThemesFactory themeFactory;

    @Mock
    private ThemesRepository themesRepository;

    @Test
    void getTheme_shouldReturnThemeSuccessful() {
        when(themesRepository.findById(BASE_THEME_ID)).thenReturn(Optional.of(BASE_THEME));

        assertEquals(BASE_THEME, themeFactory.getTheme(BASE_THEME_ID));

        verify(themesRepository, times(1)).findById(BASE_THEME_ID);
    }

    @Test
    void getTheme_shouldThrow_whenRepositoryThrow() {
        when(themesRepository.findById(BASE_THEME_ID)).thenReturn(Optional.empty());

        final NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> themeFactory.getTheme(BASE_THEME_ID));

        verify(themesRepository, times(1)).findById(BASE_THEME_ID);
    }
}