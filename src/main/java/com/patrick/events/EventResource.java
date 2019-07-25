package com.patrick.events;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
/*
public class EventResource extends ResourceSupport {

    @JsonUnwrapped
    private Event event;

    public EventResource(Event event) {
        this.event = event;
    }

    public Event getEvent(){
        return event;
    }
}
*/

/*
ResourceSupport 하위 Resource getter 부분에 이미 @JsonUnwrapped
가 있기 때문에 @JsonUnwrapped를 별도로 붙여줄 필요없이 그냥 된다.
*/
public class EventResource extends Resource<Event> {

    public EventResource(Event event, Link... links) {
        super(event, links);
        add(linkTo(Event.class).slash(event.getId()).withSelfRel());
    }
}