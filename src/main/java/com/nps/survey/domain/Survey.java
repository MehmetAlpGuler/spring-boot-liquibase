package com.nps.survey.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author MehmetAlpGuler
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Survey {

    @Id
    @Column(name = "id", columnDefinition = "serial")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String json;
    private LocalDate created;
    private LocalDate updated;
    private String creator;
}
