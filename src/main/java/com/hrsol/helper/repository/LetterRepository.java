package com.hrsol.helper.repository;

import com.hrsol.helper.entity.Letter;
import com.hrsol.helper.entity.LetterType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface LetterRepository extends PagingAndSortingRepository<Letter, Long> {

    List<Letter> findByLetterType(LetterType letterType);

    Integer countByLetterType(LetterType letterType);

    List<Letter> findByLetterType(LetterType letterType, Pageable pageable);


}
