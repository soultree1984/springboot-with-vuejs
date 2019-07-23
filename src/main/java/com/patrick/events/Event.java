package com.patrick.events;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder @AllArgsConstructor @NoArgsConstructor @Getter @Setter
@EqualsAndHashCode(of = "id")
@Entity
public class Event {

    @Id @GeneratedValue
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime beginEnrollmentDateTime;
    private LocalDateTime closeEnrollmentDateTime;
    private LocalDateTime beginEventDateTime;
    private LocalDateTime endEventDateTime;
    private String location; // (optional) 이게 없으면 온라인 모임
    private int basePrice; // (optional)
    private int maxPrice; // (optional)
    private int limitOfEnrollment;
    private boolean offline;
    private boolean free;

    @Enumerated(EnumType.STRING)

    //@Enumerated(EnumType.ORDINAL)
    /*
        EnumType.ORDINAL 로 했을 경우  Enum 값이 순서대로 0,1,2 로 저장되는데
        나중에라도 값의 순서가 바뀌었을때 오류가 발생 할 수 있기 때문에
        EnumType.STRING 으로 지정해서 값 그대로 저장되도록 옵션을 주는게 좋다.
    */
    private EventStatus eventStatus = EventStatus.DRAFT;

}

