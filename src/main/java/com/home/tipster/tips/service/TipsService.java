package com.home.tipster.tips.service;

import com.home.tipster.tips.model.Tips;

import java.util.List;

/**
 * @author Andrey Boyarov
 */
public interface TipsService {

    Tips create(Tips tips);

    Tips update(Tips tips);

    List<Tips> getAll(long themeId);

    Tips getById(long tipId);
}
