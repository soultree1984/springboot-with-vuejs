package com.patrick.events;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventTest {

    @Test
    public void builder(){
       Event event = Event.builder()
               .name("I am IronMan")
               .description("Rest API development with spring")
               .build();
       assertThat(event).isNotNull();
    }

    @Test
    public void javaBean(){

        Event event = new Event();
        String name = "Event";
        String description = "spring";

        event.setName(name);
        event.setDescription(description);

        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);

    }
}