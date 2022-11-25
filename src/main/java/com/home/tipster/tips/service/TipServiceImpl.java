package com.home.tipster.tips.service;

import com.home.tipster.themes.repository.ThemesRepository;
import com.home.tipster.tips.mapper.TipMapper;
import com.home.tipster.tips.model.Tip;
import com.home.tipster.tips.repository.TipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Andrey Boyarov
 */
@Service
@RequiredArgsConstructor
public class TipServiceImpl implements TipService {

    private final TipRepository tipRepository;
    private final ThemesRepository themesRepository;
    private final TipMapper tipMapper;

    @Override
    public Tip create(Tip tip) {
        validThemeId(tip.getTheme().getId());
        return tipRepository.save(tip);
    }

    @Override
    public Tip update(Tip tip) {
        Tip oldTip = validTipId(tip.getId());
        tipMapper.update(tip, oldTip);
        return tipRepository.save(oldTip);
    }

    @Override
    public List<Tip> getAll(long themeId) {
        validThemeId(themeId);
        return tipRepository.findByThemeId(themeId);
    }

    @Override
    public Tip getById(long tipId) {
        return validTipId(tipId);
    }

    private Tip validTipId(Long id) {
        return tipRepository.findById(id).orElseThrow(
                () -> new RuntimeException(
                        String.format("The tips with id = %d wasn't found", id)));
    }

    private void validThemeId(long themeId) {
        themesRepository.findById(themeId).orElseThrow(
                () -> new RuntimeException(
                        String.format("The themes with id = %d wasn't found", themeId)));
    }
}
