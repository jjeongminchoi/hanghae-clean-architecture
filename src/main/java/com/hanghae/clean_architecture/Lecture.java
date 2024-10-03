package com.hanghae.clean_architecture;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Lecture {

    private final int MAX_RESERVATION_COUNT = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long id;

    private String title;

    private String lecturer;

    private LocalDate lectureDate;

    private LocalTime lectureTime;

    private Lecture(String title, String lecturer, LocalDate lectureDate, LocalTime lectureTime) {
        this.title = title;
        this.lecturer = lecturer;
        this.lectureDate = lectureDate;
        this.lectureTime = lectureTime;
    }

    public static Lecture of(String title, String lecturer, LocalDate lectureDate, LocalTime lectureTime) {
        return new Lecture(title, lecturer, lectureDate, lectureTime);
    }
}
