package com.patrick.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patrick.events.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
//@WebMvcTest
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
@ActiveProfiles("test")
/*
 기존에 있는 application.properties 속성에
 application-test.properties 속성을 더해서(overriding) 테스트를 실행.
*/
@Slf4j
@Ignore //테스트를 가지고 있지 않기 때문에 Ignore를 붙여줌.
public class BaseTestContoller {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    //slice 테스트라서( @WebMvcTest ) 웹용 빈들만 등록해주고 Repository bean 들을 등록하지 않는다.

    /*
    @MockBean
    EventRepository eventRepository;
     */

    @Autowired
    protected EventRepository eventRepository;

    @Autowired
    protected ModelMapper modelMapper;
}
