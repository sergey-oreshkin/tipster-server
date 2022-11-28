package com.home.tipster.themes.controller;

import com.home.tipster.themes.dto.ThemeDto;
import com.home.tipster.themes.model.Theme;
import com.home.tipster.themes.service.ThemesMapper;
import com.home.tipster.themes.service.ThemesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/themes")
@RequiredArgsConstructor
public class ThemesController {
    private final ThemesMapper themesMapper;
    private final ThemesService themesService;

    @PostMapping
    public ThemeDto create(@Valid @RequestBody ThemeDto themeDto) {
        log.info("create themes Title={}", themeDto.getTitle());
        Theme theme = themesMapper.toThemes(themeDto);
        return themesMapper.toDto(themesService.create(theme));
    }

    @GetMapping
    public List<ThemeDto> getAll() {
        return themesMapper.toDto(themesService.getAll());
    }
}
