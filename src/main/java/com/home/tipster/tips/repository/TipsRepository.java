package com.home.tipster.tips.repository;

import com.home.tipster.tips.model.Tips;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Andrey Boyarov
 */
public interface TipsRepository extends JpaRepository<Tips, Long> {

    @Query("SELECT c FROM Tips c WHERE c.themes.id = :themeId")
    List<Tips> finaAllByThemesId(@Param("themeId") Long themeId);
}
