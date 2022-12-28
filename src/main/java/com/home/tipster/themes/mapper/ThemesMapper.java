package com.home.tipster.themes.mapper;

import com.home.tipster.themes.dto.ThemeDto;
import com.home.tipster.themes.model.Theme;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ThemesMapper {

    Theme toThemes(ThemeDto themeDto);

    ThemeDto toDto(Theme theme);

    List<ThemeDto> toDto(List<Theme> themes);


}
