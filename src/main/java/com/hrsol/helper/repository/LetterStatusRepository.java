package com.hrsol.helper.repository;

import com.hrsol.helper.entity.LetterStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterStatusRepository extends JpaRepository<LetterStatus, Long> {

    LetterStatus findByType(String type);

}
