package com.home.tipster.themes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.tipster.exception.ConflictException;
import com.home.tipster.themes.dto.ThemeDto;
import com.home.tipster.themes.mapper.ThemesMapper;
import com.home.tipster.themes.model.Theme;
import com.home.tipster.themes.service.ThemesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ThemesController.class)
@AutoConfigureMockMvc(addFilters = false)
class ThemesControllerTest {
    @MockBean
    private ThemesService themesService;

    @MockBean
    private ThemesMapper themesMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URI = "/themes";

    private static final Long BASE_THEME_ID = 22L;

    private static final Long ANOTHER_THEME_ID = 33L;

    public static final String BASE_THEME_TITLE = "Theme title test";

    public static final String BLANK_THEME_TITLE = "";

    public static final String DUPLICATE_THEME_TITLE = "Theme title test";

    public static final String ANOTHER_THEME_TITLE = "Another theme title";

    public static final Theme BASE_THEME = Theme.builder().id(BASE_THEME_ID).title(BASE_THEME_TITLE).build();

    public static final Theme ANOTHER_THEME = Theme.builder().id(ANOTHER_THEME_ID).title(ANOTHER_THEME_TITLE).build();

    public static final ThemeDto BASE_THEME_DTO = ThemeDto.builder().id(BASE_THEME_ID).title(BASE_THEME_TITLE).build();

    public static final ThemeDto BLANK_THEME_DTO = ThemeDto.builder().title(BLANK_THEME_TITLE).build();

    public static final ThemeDto DUPLICATE_THEME_DTO = ThemeDto.builder().title(DUPLICATE_THEME_TITLE).build();

    public static final ThemeDto ANOTHER_THEME_DTO = ThemeDto.builder().title(ANOTHER_THEME_TITLE).build();

    @Test
    void create_whenValidInput_thenReturnSameThemeWithStatusOk() throws Exception {

        when(themesMapper.toThemes(BASE_THEME_DTO)).thenReturn(BASE_THEME);
        when(themesMapper.toDto(BASE_THEME)).thenReturn(BASE_THEME_DTO);
        when(themesService.create(BASE_THEME)).thenReturn(BASE_THEME);

        final MvcResult mvcResult = mockMvc.perform(
                        post(BASE_URI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(BASE_THEME_DTO)))
                .andExpect(status().isOk())
                .andReturn();

        verify(themesService, times(1)).create(BASE_THEME);

        assertEquals(objectMapper.writeValueAsString(BASE_THEME_DTO), mvcResult.getResponse().getContentAsString());
    }

    @Test
    void create_whenBlankInput_thenReturnExceptionWithStatusBadRequest() throws Exception {

        mockMvc.perform(
                        post(BASE_URI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(BLANK_THEME_DTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void create_whenNotUniqueInputThemes_thenReturnExceptionWithStatusConflict() throws Exception {

        when(themesMapper.toThemes(DUPLICATE_THEME_DTO)).thenReturn(BASE_THEME);
        when(themesMapper.toDto(BASE_THEME)).thenReturn(DUPLICATE_THEME_DTO);
        when(themesService.create(BASE_THEME)).thenThrow(ConflictException.class);

        mockMvc.perform(
                        post(BASE_URI)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(DUPLICATE_THEME_DTO)))
                .andExpect(status().isConflict());
    }

    @Test
    void getAll_whenValidInput_thenReturnListOfThemesWithStatusOk() throws Exception {
        final List<Theme> themes = List.of(BASE_THEME, ANOTHER_THEME);
        final List<ThemeDto> themesDto = List.of(BASE_THEME_DTO, ANOTHER_THEME_DTO);

        when(themesService.getAll()).thenReturn(themes);
        when(themesMapper.toDto(themes)).thenReturn(themesDto);

        MvcResult mvcResult = mockMvc.perform(
                        get(BASE_URI))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(objectMapper.writeValueAsString(themesDto), mvcResult.getResponse().getContentAsString());
    }
}