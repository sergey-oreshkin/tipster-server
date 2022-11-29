package com.home.tipster.tips.repository;

import com.home.tipster.tips.model.Tip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Andrey Boyarov
 */
public interface TipRepository extends JpaRepository<Tip, Long> {

    List<Tip> findAllByThemeId(Long themeId);
}
