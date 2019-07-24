package com.patrick.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patrick.common.TestDescription;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
//@WebMvcTest
@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    //slice 테스트라서( @WebMvcTest ) 웹용 빈들만 등록해주고 Repository bean 들을 등록하지 않는다.

    /*
    @MockBean
    EventRepository eventRepository;
     */

    @Test
    @TestDescription("정상적으로 이벤트를 생성하는 테스트")
    public void createEvent() throws Exception{

        EventDto event = EventDto.builder()
                    .name("Spring")
                    .description("REST API Development with Spring")
                    .beginEnrollmentDateTime(LocalDateTime.of(2019,7,11,4,30))
                    .closeEnrollmentDateTime(LocalDateTime.of(2019,7,12,4,30))
                    .beginEventDateTime(LocalDateTime.of(2019,7,13,4,30))
                    .endEventDateTime(LocalDateTime.of(2019,7,14,4,30))
                    .basePrice(100)
                    .maxPrice(200)
                    .limitOfEnrollment(100)
                    .location("강남역 D2 스타트업 팩토리")
                    .build();

        // eventRepository.save(event) 가 호출 되면 event를 리턴하라고 지정해줌.
        //Mockito.when(eventRepository.save(event)).thenReturn(event);

        mockMvc.perform(post("/api/events/")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaTypes.HAL_JSON)
                        .content(this.objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE,MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("id").value(Matchers.not(100))) // 값이 들어오면 안될 경우 입력 제한
                .andExpect(jsonPath("free").value(Matchers.not(true)))  // 값이 들어오면 안될 경우 입력 제한
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()))
                /*
                    EventDto 에 선언된 값이 아닌 다른 값들은 무시됨.  EventStatus.PUBLISHED 로 저장하려고 했지만
                    무시되고 기본값인 EventStatus.DRAFT로 저장
                 */
        ;
    }

    @Test
    @TestDescription("입력 받을 수 없는 값을 사용한 경우에 에러가 발생하는 테스트")
    public void createEvent_Bad_Request() throws Exception{

        Event event = Event.builder()
                .id(100)
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2019,7,11,4,30))
                .closeEnrollmentDateTime(LocalDateTime.of(2019,7,12,4,30))
                .beginEventDateTime(LocalDateTime.of(2019,7,13,4,30))
                .endEventDateTime(LocalDateTime.of(2019,7,14,4,30))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타트업 팩토리")
                .free(true) //EventDto 에 선언된 값이 아닌 다른 값들은 무시됨.
                .offline(false) //EventDto 에 선언된 값이 아닌 다른 값들은 무시됨.
                .eventStatus(EventStatus.PUBLISHED) //EventDto 에 선언된 값이 아닌 다른 값들은 무시됨.
                .build();

        // eventRepository.save(event) 가 호출 되면 event를 리턴하라고 지정해줌.
        //Mockito.when(eventRepository.save(event)).thenReturn(event);

        mockMvc.perform(post("/api/events/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(this.objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isBadRequest())

        //받을 수 없는 파라미터 (unknown-properties) 가 들어오면 자동으로 Bad Request 처리 해줌.
        //spring.jackson.deserialization.fail-on-unknown-properties=true

        ;
    }

    @Test
    @TestDescription("입력값이 비어있는 경우에 에러가 발생하는 테스트")
    public void createEvent_Bad_Request_Empty_Input() throws Exception{

        EventDto eventDto = EventDto.builder().build();

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(this.objectMapper.writeValueAsString(eventDto)))

                .andExpect(status().isBadRequest());

    }


    @Test
    @TestDescription("입력값이 잘못된 경우에 에러가 발생하는 테스트")
    public void createEvent_Bad_Request_Wrong_Input() throws Exception{

        EventDto eventDto = EventDto.builder()
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2019,7,11,4,30))
                .closeEnrollmentDateTime(LocalDateTime.of(2019,7,10,4,30))
                .beginEventDateTime(LocalDateTime.of(2019,7,13,4,30))
                .endEventDateTime(LocalDateTime.of(2019,7,12,4,30))
                .basePrice(100000)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타트업 팩토리")
                .build();

        mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(this.objectMapper.writeValueAsString(eventDto)))

                .andDo(print())

                //.andExpect(jsonPath("$[0].field").exists())

                .andExpect(jsonPath("$[0].objectName").exists())
                .andExpect(jsonPath("$[0].defaultMessage").exists())
                .andExpect(jsonPath("$[0].code").exists())

                //.andExpect(jsonPath("$[0].rejectValue").exists())

                .andExpect(status().isBadRequest());

    }

}
