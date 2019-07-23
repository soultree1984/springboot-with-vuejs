package com.patrick.events;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Controller
@RequestMapping(value="/api/events",produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class EventController {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    ModelMapper modelMapper;

    /*
        스프링 4.3 버전 부터 지원

        생성자가 하나이고 생성자의 파라미터가 이미 빈으로 등록된 경우라면
        @Autowired Annotaion  생략 가능

        private final EventRepository eventRepository;

        public EventController(EventRepository eventRepository){
            this.eventRepository = eventRepository;
        }
    */

    @PostMapping
    public ResponseEntity createEvent(@RequestBody EventDto eventDto){

        Event event = modelMapper.map(eventDto,Event.class);
        Event newEvent = eventRepository.save(event);
        URI createUri = linkTo(EventController.class).slash(newEvent.getId()).toUri();
        return ResponseEntity.created(createUri).body(event);
    }
}
