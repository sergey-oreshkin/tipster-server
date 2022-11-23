package com.home.tipster.tips.service;

import com.home.tipster.themes.repository.ThemesRepository;
import com.home.tipster.tips.mapper.TipsMapper;
import com.home.tipster.tips.model.Tips;
import com.home.tipster.tips.repository.TipsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Andrey Boyarov
 */
@Service
@RequiredArgsConstructor
public class TipsServiceImpl implements TipsService {

    private final TipsRepository tipsRepository;
    private final ThemesRepository themesRepository;
    private final TipsMapper tipsMapper;

    @Override
    public Tips create(Tips tips) {
        tips.setCreate(LocalDateTime.now());
        return tipsRepository.save(tips);
    }

    @Override
    public Tips update(Tips tips) {
        validId(tips.getId());
        Tips oldTips = tipsRepository.findById(tips.getId()).orElseThrow();
        tipsMapper.update(tips, oldTips);
        oldTips.setUpdate(LocalDateTime.now());
        return tipsRepository.save(oldTips);
    }

    @Override
    public List<Tips> getAll(long themeId) {
        themesRepository.findById(themeId).orElseThrow(
                () -> new RuntimeException(
                        String.format("The themes with id = %d wasn't found", themeId)));
        return tipsRepository.finaAllByThemesId(themeId);
    }

    @Override
    public Tips getById(long tipId) {
        validId(tipId);
        return tipsRepository.findById(tipId).orElseThrow();
    }

    private void validId(Long id) {
        tipsRepository.findById(id).orElseThrow(
                () -> new RuntimeException(
                        String.format("The tips with id = %d wasn't found", id)));
    }
}
