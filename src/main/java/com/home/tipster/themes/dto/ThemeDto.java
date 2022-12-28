package com.home.tipster.themes.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class ThemeDto {

    private Long id;
    @NotBlank(message = "title is blank")
    private String title;
}
