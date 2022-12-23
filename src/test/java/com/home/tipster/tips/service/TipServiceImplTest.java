package com.home.tipster.tips.service;

import com.home.tipster.exception.NotFoundException;
import com.home.tipster.themes.factory.ThemesFactory;
import com.home.tipster.themes.model.Theme;
import com.home.tipster.tips.factory.TipFactory;
import com.home.tipster.tips.mapper.TipMapper;
import com.home.tipster.tips.model.Tip;
import com.home.tipster.tips.repository.TipRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


/**
 * @author Andrey Boyarov
 */
@ExtendWith(MockitoExtension.class)
class TipServiceImplTest {

    private static final Long BASE_TIP_ID = 11L;

    private static final Long BASE_THEME_ID = 22L;

    public static final Long BASE_NEW_TIP_ID = 33L;

    public static final String BASE_THEME_TITLE = "Theme title";

    private static final String BASE_TIP_TITLE = "Tip1";

    private static final String BASE_TIP_TEXT = "Text of the tip";

    public static final String NOT_FOUND_MESSAGE = "not found";
    public static final Theme BASE_THEME = Theme.builder().id(BASE_THEME_ID).title(BASE_THEME_TITLE).build();

    private static final Tip BASE_TIP = Tip.builder()
            .id(BASE_TIP_ID)
            .title(BASE_TIP_TITLE)
            .text(BASE_TIP_TEXT)
            .theme(BASE_THEME)
            .build();

    private static final Tip BASE_NEW_TIP = Tip.builder()
            .id(BASE_NEW_TIP_ID)
            .title(BASE_TIP_TITLE)
            .text(BASE_TIP_TEXT)
            .theme(BASE_THEME)
            .build();

    @InjectMocks
    private TipServiceImpl tipService;
    @Mock
    private TipRepository tipRepository;
    @Mock
    private ThemesFactory themesFactory;
    @Mock
    private TipFactory tipFactory;

    @Mock
    private TipMapper tipMapper;

    @Test
    void create_shouldCreateTipSuccessful() {

        when(tipRepository.save(BASE_TIP)).thenReturn(BASE_TIP);

        assertEquals(BASE_TIP, tipService.create(BASE_TIP));

        verify(themesFactory, times(1)).getTheme(BASE_TIP.getTheme().getId());
        verify(tipRepository, times(1)).save(BASE_TIP);
    }

    @Test
    void create_shouldThrow_WhenRepositoryThrow(){

        when(tipRepository.save(BASE_TIP)).thenThrow(new NotFoundException(NOT_FOUND_MESSAGE));

        final NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> tipService.create(BASE_TIP));

        assertEquals(NOT_FOUND_MESSAGE, exception.getMessage());

    }

    @Test
    void update_shouldUpdateTipSuccessful() {

        when(tipFactory.getTip(BASE_NEW_TIP_ID)).thenReturn(BASE_TIP);
        when(tipRepository.save(BASE_TIP)).thenReturn(BASE_TIP);

        assertEquals(BASE_TIP, tipService.update(BASE_NEW_TIP));

        verify(tipMapper, times(1)).update(BASE_NEW_TIP, BASE_TIP); // Проверяется порядок аргументов
        verify(tipRepository, times(1)).save(BASE_TIP);
    }

    @Test()
    void update_shouldThrow_WhenFactoryThrow(){

        when(tipFactory.getTip(BASE_TIP_ID)).thenThrow(new NotFoundException(NOT_FOUND_MESSAGE));

        final NotFoundException exception = assertThrows(
                NotFoundException.class,
                ()-> tipService.update(BASE_TIP));

        assertEquals(NOT_FOUND_MESSAGE, exception.getMessage());
    }

    @Test
    void getAll_shouldReturnAllTips() {

        when(tipRepository.findAllByThemeId(BASE_THEME_ID)).thenReturn(List.of(BASE_TIP));

        assertEquals(List.of(BASE_TIP), tipService.getAll(BASE_THEME_ID));

        verify(tipRepository, times(1)).findAllByThemeId(BASE_THEME_ID);
    }

    @Test
    void getById_shouldReturnTipByIdSuccessful() {

        when(tipFactory.getTip(BASE_TIP_ID)).thenReturn(BASE_TIP);

        assertEquals(BASE_TIP, tipService.getById(BASE_TIP_ID));

        verify(tipFactory, times(1)).getTip(BASE_TIP_ID);
    }

    @Test
    void getById_shouldThrow_WhenFactoryThrow(){
        when(tipFactory.getTip(BASE_TIP_ID)).thenThrow(new NotFoundException(NOT_FOUND_MESSAGE));

        final NotFoundException exception = assertThrows(
                NotFoundException.class,
                ()-> tipService.getById(BASE_TIP_ID));

        assertEquals(NOT_FOUND_MESSAGE, exception.getMessage());
    }
}