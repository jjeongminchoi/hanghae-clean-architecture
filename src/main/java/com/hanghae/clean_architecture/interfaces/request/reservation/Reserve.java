package com.hanghae.clean_architecture.interfaces.request.reservation;

import lombok.Getter;

@Getter
public class Reserve {

    private Long lectureId;

    public Reserve(Long lectureId) {
        this.lectureId = lectureId;
    }
}
