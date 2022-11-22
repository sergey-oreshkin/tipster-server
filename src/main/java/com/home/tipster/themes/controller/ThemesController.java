package com.home.tipster.themes.controller;

import com.home.tipster.themes.dto.ThemesDto;
import com.home.tipster.themes.model.Themes;
import com.home.tipster.themes.service.ThemesMapper;
import com.home.tipster.themes.service.ThemesServiceImpl;
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
    private final ThemesServiceImpl themesService;

    @PostMapping
    public ThemesDto create(@Valid @RequestBody ThemesDto themesDto) {
        log.info("create themes Title={}", themesDto.getTitle());
        Themes themes = themesMapper.toThemes(themesDto);
        return themesMapper.toDto(themesService.create(themes));
    }

    @GetMapping
    public List<ThemesDto> getAll(){
        return themesMapper.toDto(themesService.getAll());
    }
}
