package com.home.tipster.themes.service;

import com.home.tipster.exception.ConflictException;
import com.home.tipster.themes.model.Theme;
import com.home.tipster.themes.repository.ThemesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ThemesServiceTest {

    private static final Long BASE_THEME_ID = 22L;

    private static final Long ANOTHER_THEME_ID = 33L;

    public static final String BASE_THEME_TITLE = "Theme title test";

    public static final String ANOTHER_THEME_TITLE = "Another theme title";

    public static final String CONFLICT_MESSAGE = "This title already exists";

    public static final Theme BASE_THEME = Theme.builder().id(BASE_THEME_ID).title(BASE_THEME_TITLE).build();

    public static final Theme ANOTHER_THEME = Theme.builder().id(ANOTHER_THEME_ID).title(ANOTHER_THEME_TITLE).build();

    @InjectMocks
    private ThemesServiceImpl themesService;
    @Mock
    private ThemesRepository themesRepository;

    @Test
    void create_shouldCreateThemeSuccessful() {
        when(themesRepository.save(BASE_THEME)).thenReturn(BASE_THEME);

        assertEquals(BASE_THEME, themesService.create(BASE_THEME));

        verify(themesRepository, times(1)).save(BASE_THEME);
    }

    @Test
    void create_shouldThrow_WhenRepositoryThrow() {
        when(themesRepository.save(BASE_THEME)).thenThrow(
                new DataIntegrityViolationException(CONFLICT_MESSAGE));

        final ConflictException exception = assertThrows(
                ConflictException.class,
                () -> themesService.create(BASE_THEME));
    }

    @Test
    void getAll_shouldReturnAllThemes() {
        when(themesRepository.findAll()).thenReturn(List.of(BASE_THEME, ANOTHER_THEME));

        assertEquals(List.of(ANOTHER_THEME, BASE_THEME), themesService.getAll());

        verify(themesRepository, times(1)).findAll();
    }
}