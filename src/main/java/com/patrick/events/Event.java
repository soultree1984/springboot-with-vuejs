package com.patrick.events;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.patrick.accounts.Account;
import com.patrick.accounts.AccountSerializer;
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


    /*
        AccountSerializer를 @JsonComponent 로 등록하면
        Account 를 내려줄때 무조건 ID만 보여지게 되는데,
        만약다른 페이지에서는 Account의 다른 정보가
        보여질 필요가 있다면 Event하위에서 세팅 할때만
        선택적으로 Serialize 하고싶다면
        @JsonSerialize(using = AccountSerializer.class)
        로 선언해준다.
    */
    @ManyToOne
    @JsonSerialize(using = AccountSerializer.class)
    private Account manager;



    //@Enumerated(EnumType.ORDINAL)
    /*
        EnumType.ORDINAL 로 했을 경우  Enum 값이 순서대로 0,1,2 로 저장되는데
        나중에라도 값의 순서가 바뀌었을때 오류가 발생 할 수 있기 때문에
        EnumType.STRING 으로 지정해서 값 그대로 저장되도록 옵션을 주는게 좋다.
    */
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus = EventStatus.DRAFT;

    public void update() {

        //update Free
        if(this.basePrice == 0 && this.maxPrice == 0){
            this.free = true;
        }else{
            this.free = false;
        }

        //update location
        if(this.location == null || this.location.isBlank()){
            this.offline = false;
        }else{
            this.offline = true;
        }

    }
}

