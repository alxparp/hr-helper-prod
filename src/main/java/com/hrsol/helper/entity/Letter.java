package com.hrsol.helper.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "letter_t")
public class Letter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Date dueDate;

    @ManyToOne
    @JoinColumn(name = "letter_status_id")
    private LetterStatus letterStatus;

    @ManyToOne
    @JoinColumn(name = "username")
    private User username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "letter_type_id")
    private LetterType letterType;

    @ManyToOne
    @JoinColumn(name = "template_type_id")
    private TemplateType templateType;

}
