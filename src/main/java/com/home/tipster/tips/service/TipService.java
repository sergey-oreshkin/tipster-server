package com.home.tipster.tips.service;

import com.home.tipster.tips.model.Tip;

import java.util.List;

/**
 * @author Andrey Boyarov
 */
public interface TipService {

    Tip create(Tip tip);

    Tip update(Tip tip);

    List<Tip> getAll(Long themeId);

    Tip getById(Long tipId);
}
