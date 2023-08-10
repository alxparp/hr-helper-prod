package com.hrsol.helper.repository;

import com.hrsol.helper.entity.Letter;
import com.hrsol.helper.entity.LetterType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LetterRepository extends JpaRepository<Letter, Long> {

    List<Letter> findByLetterType(LetterType letterType);

    Integer countByLetterType(LetterType letterType);

    List<Letter> findByLetterType(LetterType letterType, Pageable pageable);

    @Modifying
    @Query("UPDATE Letter l " +
            "SET l.letterType = :letterType " +
            "WHERE l.id = :id")
    int approveGeneratedLetter(@Param("letterType") LetterType letterType,
                                   @Param("id") Long id);

    List<Letter> findByLetterTypeAndUsername_Location_CityIn(LetterType letterType, List<String> cities, Pageable pageable);

    Integer countByLetterTypeAndUsername_Location_CityIn(LetterType letterType, List<String> cities);


}
