package com.hanghae.clean_architecture.interfaces.api.reservation;

import com.hanghae.clean_architecture.domain.reservation.service.ReservationService;
import com.hanghae.clean_architecture.interfaces.request.Reserve;
import com.hanghae.clean_architecture.interfaces.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/reservation")
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/{userId}")
    public ResponseEntity<?> createReservation(@PathVariable Long userId, @RequestBody Reserve reserve) {
        return ResponseEntity.ok(new ResponseDto<>("특강 신청에 성공했습니다.", reservationService.reserve(userId, reserve)));
    }

}
