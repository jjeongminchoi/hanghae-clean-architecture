package com.hanghae.clean_architecture.domain.reservation;

import com.hanghae.clean_architecture.domain.reservation.constant.ReservationStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "lecture_id")
    private Long lectureId;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    private Reservation(Long userId, Long lectureId) {
        this.userId = userId;
        this.lectureId = lectureId;
        this.reservationStatus = ReservationStatus.ACTIVE;
    }

    public static Reservation create(Long userId, Long lectureId) {
        return new Reservation(userId, lectureId);
    }
}
