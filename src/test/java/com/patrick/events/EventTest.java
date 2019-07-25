package com.patrick.events;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
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

    @Test
    /*
    @Parameters({
            "0, 0, true",
            "100,0,false",
            "0,100,false"
    })
    */

    //@Parameters(method = "parametersForTestFree") //parametersFor 가 prefix 라서 이름 생략 가능
    @Parameters
    public void testFree(int basePrice,int maxPrice,boolean isFree){

        //Given
        Event event = Event.builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();

        //When
        event.update();

        //Then
        assertThat(event.isFree()).isEqualTo(isFree);
    }

    private Object[] parametersForTestFree(){
        return new Object[]{
                new Object[] {0,0,true},
                new Object[] {100,0,false},
                new Object[] {0,100,false},
                new Object[] {100,200,false}
        };
    }

    @Test
    @Parameters
    public void testOffLine(String location,boolean isOffLine){

        //Given
        Event event = Event.builder()
                .location(location)
                .build();

        //When
        event.update();

        //Then
        assertThat(event.isOffline()).isEqualTo(isOffLine);

    }

    private Object[] parametersForTestOffLine(){
        return new Object[]{
                new Object[] {"강남",true},
                new Object[] {null,false},
                new Object[] {"           ",false}
        };
    }
}