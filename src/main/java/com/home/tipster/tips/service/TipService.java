package com.home.tipster.tips.service;

import com.home.tipster.exception.NotFoundException;
import com.home.tipster.tips.model.Tip;

import java.util.List;

/**
 * @author Andrey Boyarov
 */
public interface TipService {

    Tip create(Tip tip);

    Tip update(Tip tip);

    List<Tip> getAll(long themeId);

    Tip getById(long tipId);
}
