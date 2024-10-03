package com.hanghae.clean_architecture.interfaces.response.reservation;

import com.hanghae.clean_architecture.domain.reservation.constant.ReservationStatus;
import lombok.Getter;

@Getter
public class ReservationResponse {

    private Long lectureId;

    private String title;

    private String lecturer;

    private ReservationStatus reservationStatus;

    public ReservationResponse(Long lectureId, String title, String lecturer, ReservationStatus reservationStatus) {
        this.lectureId = lectureId;
        this.title = title;
        this.lecturer = lecturer;
        this.reservationStatus = reservationStatus;
    }
}
