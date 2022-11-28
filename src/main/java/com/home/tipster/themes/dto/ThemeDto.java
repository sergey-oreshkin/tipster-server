package com.home.tipster.themes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThemeDto {

    private Long id;
    @NotBlank(message = "title is blank")
    private String title;
}
