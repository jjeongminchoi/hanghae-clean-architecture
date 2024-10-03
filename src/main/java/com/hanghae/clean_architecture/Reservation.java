package com.hanghae.clean_architecture;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    private Reservation(Users user, Lecture lecture) {
        this.user = user;
        this.lecture = lecture;
        this.reservationStatus = ReservationStatus.ACTIVE;
    }

    public static Reservation create(Users user, Lecture lecture) {
        return new Reservation(user, lecture);
    }
}
