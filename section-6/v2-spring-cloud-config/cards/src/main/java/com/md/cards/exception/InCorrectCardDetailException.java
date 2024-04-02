package com.md.cards.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class InCorrectCardDetailException extends RuntimeException{

        public InCorrectCardDetailException(String message){
            super(message);
        }

}
