package com.home.tipster.tips.mapper;

import com.home.tipster.tips.model.Tips;
import com.home.tipster.tips.model.TipsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * @author Andrey Boyarov
 */

@Mapper(componentModel = "spring")
public interface TipsMapper {

    Tips toTips(TipsDto tipsDto);

    TipsDto toDto(Tips tips);

    List<TipsDto> toDto(List<Tips> tips);

    void update (Tips tips, @MappingTarget Tips oldTips);


}
