package com.patrick.events;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

@Component
public class EventValidator {

    public void validate(EventDto eventDto, Errors errors){

        if(eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() != 0){
            //errors.rejectValue("basePrice","wrongValue","BasePrice is wrong");
            //errors.rejectValue("maxPrice","wrongValue","MaxPrice is wrong"); // Field Error
            errors.reject("Prices","Prices is wrong"); // Global Error
        }

        LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();

        if(endEventDateTime.isBefore(eventDto.getBeginEventDateTime()) ||
            endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
            endEventDateTime.isBefore(eventDto.getBeginEventDateTime())){

            errors.rejectValue("endEventDateTime","wrongValue","EndEventDateTime is wrong");
        }

        // TODO beginEventDateTime
        // TODO closeEventDateTime

    }

}
