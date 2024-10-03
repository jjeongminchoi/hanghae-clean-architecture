package com.hanghae.clean_architecture.domain.reservation.service;

import com.hanghae.clean_architecture.interfaces.request.reservation.Reserve;
import com.hanghae.clean_architecture.interfaces.response.reservation.ReservationResponse;

import java.util.List;

public interface ReservationService {

    Long reserve(Long userId, Reserve reserve);

    List<ReservationResponse> searchReservations(Long userId);
}
