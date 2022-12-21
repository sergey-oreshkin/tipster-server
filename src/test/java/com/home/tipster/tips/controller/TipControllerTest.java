package com.home.tipster.tips.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.tipster.exception.NotFoundException;
import com.home.tipster.themes.model.Theme;
import com.home.tipster.tips.mapper.TipMapper;
import com.home.tipster.tips.model.Tip;
import com.home.tipster.tips.model.TipDto;
import com.home.tipster.tips.service.TipService;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = TipController.class)
class TipControllerTest {

    private static final String BASE_URI = "/tips";

    private static final Long BASE_TIP_ID = 11L;

    private static final Long BASE_THEME_ID = 22L;

    public static final String BASE_THEME_TITLE = "Theme title";

    private static final String BASE_TIP_TITLE = "Tip1";

    private static final String BASE_TIP_TEXT = "Text of the tip";

    public static final Theme BASE_THEME = Theme.builder().id(BASE_THEME_ID).title(BASE_THEME_TITLE).build();

    private static final Tip BASE_TIP = Tip.builder()
            .id(BASE_TIP_ID)
            .title(BASE_TIP_TITLE)
            .text(BASE_TIP_TEXT)
            .theme(BASE_THEME)
            .build();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TipService tipService;

    @MockBean
    private TipMapper tipMapper;

    private final TipMapper mapper = Mappers.getMapper(TipMapper.class);

    @Test
    void create_whenValidInput_thenReturnSameTipWithStatusOk() throws Exception {
        final TipDto tipDto = mapper.toDto(BASE_TIP);

        when(tipMapper.toTips(tipDto)).thenReturn(BASE_TIP);
        when(tipMapper.toDto(BASE_TIP)).thenReturn(tipDto);
        when(tipService.create(BASE_TIP)).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

        final MvcResult mvcResult = mockMvc.perform(
                        post(BASE_URI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(tipDto)))
                .andExpect(status().isOk())
                .andReturn();

        verify(tipService, times(1)).create(BASE_TIP);

        assertEquals(objectMapper.writeValueAsString(tipDto), mvcResult.getResponse().getContentAsString());
    }

    @Test
    void update_whenValidInput_thenReturnSameTipWithStatusOk() throws Exception {
        final TipDto tipDto = mapper.toDto(BASE_TIP);

        when(tipMapper.toTips(tipDto)).thenReturn(BASE_TIP);
        when(tipMapper.toDto(BASE_TIP)).thenReturn(tipDto);
        when(tipService.update(BASE_TIP)).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);

        final MvcResult mvcResult = mockMvc.perform(
                        put(BASE_URI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(tipDto)))
                .andExpect(status().isOk())
                .andReturn();

        verify(tipService, times(1)).update(BASE_TIP);

        assertEquals(objectMapper.writeValueAsString(tipDto), mvcResult.getResponse().getContentAsString());
    }

    @Test
    void getAll_whenValidInput_thenReturnListOfTipsWithStatusOk() throws Exception {
        final List<TipDto> tipDtos = List.of(mapper.toDto(BASE_TIP));

        final List<Tip> LIST_OF_BASE_TIP = List.of(BASE_TIP);

        when(tipMapper.toDto(LIST_OF_BASE_TIP)).thenReturn(tipDtos);
        when(tipService.getAll(BASE_THEME_ID)).thenReturn(LIST_OF_BASE_TIP);

        MvcResult mvcResult = mockMvc.perform(
                        get(BASE_URI).param("themeId", String.valueOf(BASE_THEME_ID)))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(objectMapper.writeValueAsString(tipDtos), mvcResult.getResponse().getContentAsString());
    }

    @Test
    void getById_whenValidInput_thenReturnSameTipWithStatusOk() throws Exception {
        final TipDto tipDto = mapper.toDto(BASE_TIP);

        when(tipMapper.toDto(BASE_TIP)).thenReturn(tipDto);
        when(tipService.getById(BASE_TIP_ID)).thenReturn(BASE_TIP);

        MvcResult mvcResult = mockMvc.perform(
                        get(BASE_URI + "/{tipId}", BASE_TIP_ID))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(objectMapper.writeValueAsString(tipDto), mvcResult.getResponse().getContentAsString());
    }

    @Test
    void create_whenServiceThrowNotFound_thenReturnStatusNotFound() throws Exception {
        final TipDto tipDto = mapper.toDto(BASE_TIP);

        when(tipService.create(any())).thenThrow(NotFoundException.class);

        mockMvc.perform(
                        post(BASE_URI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(tipDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void update_whenServiceThrowNotFound_thenReturnStatusNotFound() throws Exception {
        final TipDto tipDto = mapper.toDto(BASE_TIP);

        when(tipService.update(any())).thenThrow(NotFoundException.class);

        mockMvc.perform(
                        put(BASE_URI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(tipDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void getById_whenServiceThrowNotFound_thenReturnStatusNotFound() throws Exception {
        final TipDto tipDto = mapper.toDto(BASE_TIP);

        when(tipService.getById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(
                        get(BASE_URI + "/{tipId}", BASE_TIP_ID))
                .andExpect(status().isNotFound());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("badTipsSource")
    void create_whenInvalidInput_thenReturnBadRequestStatus(String name, TipDto tipDto) throws Exception {
        mockMvc.perform(
                        post(BASE_URI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(tipDto)))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("badTipsSource")
    void update_whenInvalidInput_thenReturnBadRequestStatus(String name, TipDto tipDto) throws Exception {
        mockMvc.perform(
                        put(BASE_URI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(tipDto)))
                .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> badTipsSource() {
        String tooLongTile = Strings.repeat("a", 101);
        return Stream.of(
                Arguments.of(
                        "Title is null",
                        TipDto.builder().text(BASE_TIP_TEXT).theme(BASE_THEME_ID).build()
                ),
                Arguments.of(
                        "Text is null",
                        TipDto.builder().title(BASE_TIP_TITLE).theme(BASE_THEME_ID).build()
                ),
                Arguments.of(
                        "Theme is null",
                        TipDto.builder().title(BASE_TIP_TITLE).text(BASE_TIP_TEXT).build()
                ),
                Arguments.of(
                        "Title is too long",
                        TipDto.builder().title(tooLongTile).text(BASE_TIP_TEXT).theme(BASE_THEME_ID).build()
                )
        );
    }
}