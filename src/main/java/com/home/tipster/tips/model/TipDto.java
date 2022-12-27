package com.home.tipster.tips.model;

import liquibase.pro.packaged.B;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Andrey Boyarov
 */
@Data
@Builder
public class TipDto {

    private Long id;
    @NotNull
    @Size(max = 100)
    private String title;
    @NotNull
    private String text;
    @NotNull
    private Long theme;

}
