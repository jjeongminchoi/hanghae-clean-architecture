package com.hanghae.clean_architecture.domain.reservation.service;

import com.hanghae.clean_architecture.interfaces.request.Reserve;

public interface ReservationService {

    Long reserve(Long userId, Reserve reserve);
}
