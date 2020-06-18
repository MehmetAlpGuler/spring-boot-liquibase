package com.nps.survey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nps.survey.domain.Survey;

/**
 * @author MehmetAlpGuler
 */
@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long>{

}
