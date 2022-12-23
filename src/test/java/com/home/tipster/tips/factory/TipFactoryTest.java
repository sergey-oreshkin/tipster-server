package com.home.tipster.tips.factory;

import com.home.tipster.exception.NotFoundException;
import com.home.tipster.themes.model.Theme;
import com.home.tipster.tips.model.Tip;
import com.home.tipster.tips.repository.TipRepository;
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
class TipFactoryTest {
    private static final Long BASE_TIP_ID = 11L;

    private static final Long BASE_THEME_ID = 22L;

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

    @InjectMocks
    private TipFactory tipFactory;

    @Mock
    private TipRepository tipRepository;

    @Test
    void getTip_shouldReturnTipSuccessful() {

        when(tipRepository.findById(BASE_TIP_ID)).thenReturn(Optional.ofNullable(BASE_TIP));

        assertEquals(BASE_TIP, tipFactory.getTip(BASE_TIP_ID));

        verify(tipRepository, times(1)).findById(BASE_TIP_ID);
    }

    @Test
    void getTip_shouldThrow_WhenRepositoryThrow() {

        when(tipRepository.findById(BASE_TIP_ID)).thenThrow(new NotFoundException(NOT_FOUND_MESSAGE));

        final NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> tipFactory.getTip(BASE_TIP_ID));

        assertEquals(NOT_FOUND_MESSAGE, exception.getMessage());
    }
}