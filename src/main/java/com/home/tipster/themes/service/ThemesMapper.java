package com.home.tipster.themes.service;

import com.home.tipster.themes.dto.ThemesDto;
import com.home.tipster.themes.model.Themes;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ThemesMapper {

    Themes toThemes(ThemesDto themesDto);

    ThemesDto toDto(Themes themes);

    List<ThemesDto> toDto(List<Themes> themes);


}
