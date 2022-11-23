package com.home.tipster.tips.controller;

import com.home.tipster.tips.mapper.TipsMapper;
import com.home.tipster.tips.model.Tips;
import com.home.tipster.tips.model.TipsDto;
import com.home.tipster.tips.service.TipsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Andrey Boyarov
 */
@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping(value = "/tips")
@RequiredArgsConstructor
public class TipsController {

    private final TipsService tipsService;

    private final TipsMapper tipsMapper;

    @PostMapping
    public TipsDto create(@RequestBody TipsDto tipsDto){
        log.info("Creates tips with title{}", tipsDto.getTitle());
        Tips tips = tipsMapper.toTips(tipsDto);
        return tipsMapper.toDto(tipsService.create(tips));
    }

    @PutMapping
    public TipsDto update(@RequestBody TipsDto tipsDto){
        log.info("Updates tips with id{}", tipsDto.getId());
        Tips tips = tipsMapper.toTips(tipsDto);
        return tipsMapper.toDto(tipsService.create(tips));
    }


    @GetMapping
    public List<TipsDto> getAll(@RequestParam(value = "{themeId}") long themeId){
        return tipsMapper.toDto(tipsService.getAll(themeId));
    }


    @GetMapping(value = "/{tipId}")
    public TipsDto getById(@PathVariable long tipId){
        return tipsMapper.toDto(tipsService.getById(tipId));
    }
}
