package com.rebirth.veterinaryexample.app.web.advices;

import com.rebirth.veterinaryexample.app.exceptions.PetCreateEx;
import com.rebirth.veterinaryexample.app.exceptions.PetNotFoundEx;
import com.rebirth.veterinaryexample.app.web.advices.errors.PetCreateProblem;
import com.rebirth.veterinaryexample.app.web.advices.errors.PetNotFoundProblem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@ControllerAdvice
class SecurityAdvice implements ProblemHandling, SecurityAdviceTrait {

    @ExceptionHandler
    public ResponseEntity<Problem> petNotFounddException(
            PetNotFoundEx ex,
            NativeWebRequest request
    ) {
        PetNotFoundProblem problem = new PetNotFoundProblem(ex.getPetUUID());
        HttpHeaders headers = new HttpHeaders();
        headers.set("app", "vetapp");
        headers.set("getEntityName", problem.getEntityName());
        headers.set("getErrorKey", problem.getErrorKey());
        headers.set("getMessage", problem.getMessage());
        return create(problem, request, headers);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> petCreateException(
            PetCreateEx ex,
            NativeWebRequest request
    ) {
        Map<String, Object> errors = ex.getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, (fieldError) -> {
                    Object value = fieldError.getRejectedValue();
                    if (value == null) return "BAD! | " + fieldError.getDefaultMessage();
                    return value + " | " + fieldError.getDefaultMessage();
                }, (s, s2) -> s, ConcurrentHashMap::new));
        errors.put("name", "PetCreate");
        errors.put("error", "BadCreations");


        PetCreateProblem problem = new PetCreateProblem(errors);
        HttpHeaders headers = new HttpHeaders();
        headers.set("app", "vetapp");
        headers.set("getEntityName", problem.getEntityName());
        headers.set("getErrorKey", problem.getErrorKey());
        headers.set("getMessage", problem.getMessage());
        return create(problem, request, headers);
    }


}