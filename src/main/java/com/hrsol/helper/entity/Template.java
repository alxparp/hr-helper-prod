package com.hrsol.helper.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String summary;

    private String firstWords;

    private String mainWords;

    private String signature;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "template_type_id")
    private TemplateType templateType;

}
