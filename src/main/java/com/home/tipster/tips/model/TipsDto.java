package com.home.tipster.tips.model;

import lombok.Data;

/**
 * @author Andrey Boyarov
 */
@Data
public class TipsDto {

    private Long id;
    private String title;
    private String text;
    private Long themes;

}
