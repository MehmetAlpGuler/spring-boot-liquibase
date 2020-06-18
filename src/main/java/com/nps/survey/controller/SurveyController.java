package com.nps.survey.controller;

import com.nps.survey.domain.Survey;
import com.nps.survey.exception.NotFoundException;
import com.nps.survey.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author MehmetAlpGuler
 */
@RestController
@RequestMapping("/api/v1")
public class SurveyController {

    @Autowired
    private SurveyRepository surveyRepository;


    @Autowired
    private MessageSource messageSource;

    @GetMapping("/surveys")
    public List<Survey> retrieveAllSurveys() {
        return surveyRepository.findAll();
    }


    @GetMapping("/surveys/{id}")
    public EntityModel<Survey> retrieveSurvey(@PathVariable long id) {
        Optional<Survey> survey = surveyRepository.findById(id);

        if (!survey.isPresent())
            throw new NotFoundException(messageSource.getMessage("not.found.message", null,
                    LocaleContextHolder.getLocale()) + " id-" + id);

        EntityModel<Survey> resource = new EntityModel<>(survey.get());

        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllSurveys());
        resource.add(linkTo.withRel("all-surveys").withType("GET"));
        return resource;
    }

    @DeleteMapping("/surveys/{id}")
    public void deleteSurvey(@PathVariable long id) {
        surveyRepository.deleteById(id);
    }


    @PostMapping("/surveys")
    public ResponseEntity<Survey> createSurvey(@RequestBody Survey survey) {
        Survey savedSurvey = surveyRepository.save(survey);

        URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedSurvey.getId()).toUri();
        return ResponseEntity.created(location).build();

    }

    @PutMapping("/surveys/{id}")
    public EntityModel<Survey> createSurvey(@PathVariable long id, @RequestBody Survey survey) {
        Optional<Survey> surveyOptional = surveyRepository.findById(id);

        if (!surveyOptional.isPresent()) {
            throw new NotFoundException(messageSource.getMessage("not.found.message", null,
                    LocaleContextHolder.getLocale()) + " id-" + id);
        }

        Survey savedSurvey = surveyRepository.save(survey);


        EntityModel<Survey> resource = new EntityModel<>(savedSurvey);

        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllSurveys());
        resource.add(linkTo.withRel("all-surveys").withType("GET"));

        return resource;

    }
}
