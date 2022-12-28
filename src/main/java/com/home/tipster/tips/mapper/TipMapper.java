package com.home.tipster.tips.mapper;

import com.home.tipster.themes.factory.ThemesFactory;
import com.home.tipster.tips.model.Tip;
import com.home.tipster.tips.model.TipDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * @author Andrey Boyarov
 */

@Mapper(componentModel = "spring",
        uses = ThemesFactory.class)
public interface TipMapper {

    Tip toTips(TipDto tipDto);

    @Mapping(target = "theme", source = "theme.id")
    TipDto toDto(Tip tip);

    List<TipDto> toDto(List<Tip> tips);

    void update(Tip tip, @MappingTarget Tip oldTip);
}
