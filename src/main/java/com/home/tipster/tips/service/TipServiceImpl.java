package com.home.tipster.tips.service;

import com.home.tipster.themes.factory.ThemesFactory;
import com.home.tipster.themes.model.Theme;
import com.home.tipster.tips.factory.TipFactory;
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
    private final ThemesFactory themesFactory;
    private final TipFactory tipFactory;
    private final TipMapper tipMapper;

    @Override
    public Tip create(Tip tip) {
        getThemeOrThrow(tip.getTheme().getId());
        return tipRepository.save(tip);
    }

    @Override
    public Tip update(Tip tip) {
        Tip oldTip = getTipOrThrow(tip.getId());
        tipMapper.update(tip, oldTip);
        return tipRepository.save(oldTip);
    }

    @Override
    public List<Tip> getAll(Long themeId) {
        return tipRepository.findAllByThemeId(themeId);
    }

    @Override
    public Tip getById(Long tipId) {
        return getTipOrThrow(tipId);
    }

    private Tip getTipOrThrow(Long id) {
        return tipFactory.getTip(id);
    }

    private Theme getThemeOrThrow(Long themeId) {
        return themesFactory.getTheme(themeId);
    }
}
