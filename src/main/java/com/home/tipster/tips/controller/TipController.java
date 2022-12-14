package com.home.tipster.tips.controller;

import com.home.tipster.tips.mapper.TipMapper;
import com.home.tipster.tips.model.Tip;
import com.home.tipster.tips.model.TipDto;
import com.home.tipster.tips.service.TipService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Andrey Boyarov
 */
@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping(value = "/tips")
@RequiredArgsConstructor
public class TipController {

    private final TipService tipService;

    private final TipMapper tipMapper;

    @PostMapping
    public TipDto create(@Valid @RequestBody TipDto tipDto) {
        log.info("Creates tips with title {}", tipDto.getTitle());
        Tip tip = tipMapper.toTips(tipDto);
        return tipMapper.toDto(tipService.create(tip));
    }

    @PutMapping
    public TipDto update(@Valid @RequestBody TipDto tipDto) {
        log.info("Updates tips with id {}", tipDto.getId());
        Tip tip = tipMapper.toTips(tipDto);
        return tipMapper.toDto(tipService.update(tip));
    }


    @GetMapping
    public List<TipDto> getAll(@RequestParam(value = "themeId") Long themeId) {
        return tipMapper.toDto(tipService.getAll(themeId));
    }


    @GetMapping(value = "/{tipId}")
    public TipDto getById(@PathVariable Long tipId) {
        return tipMapper.toDto(tipService.getById(tipId));
    }
}
