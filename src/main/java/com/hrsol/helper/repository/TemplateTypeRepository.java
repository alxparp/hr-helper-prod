package com.hrsol.helper.repository;

import com.hrsol.helper.entity.TemplateType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateTypeRepository extends JpaRepository<TemplateType, Long> {

    TemplateType findByType(String type);

}
