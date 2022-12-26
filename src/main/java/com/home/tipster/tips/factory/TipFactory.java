package com.home.tipster.tips.factory;

import com.home.tipster.exception.NotFoundException;
import com.home.tipster.tips.model.Tip;
import com.home.tipster.tips.repository.TipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Andrey Boyarov
 */

@Component
@RequiredArgsConstructor
public class TipFactory {

    private final TipRepository tipRepository;

    public Tip getTip(Long id) {
        return tipRepository.findById(id).orElseThrow(
                () -> new NotFoundException(
                        String.format("The tip with id = %d wasn't found", id)));
    }
}
