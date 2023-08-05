package com.hrsol.helper.repository;

import com.hrsol.helper.entity.Letter;
import com.hrsol.helper.entity.LetterType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LetterRepository extends JpaRepository<Letter, Long> {

    List<Letter> findByLetterType(LetterType letterType);


}
