package com.hanghae.clean_architecture;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long id;

    private String title;

    private String lecturer;

    private LocalDateTime lectureDate;

    private Lecture(String title, String lecturer, LocalDateTime lectureDate) {
        this.title = title;
        this.lecturer = lecturer;
        this.lectureDate = lectureDate;
    }

    public static Lecture create(String title, String lecturer, LocalDateTime lectureDate) {
        return new Lecture(title, lecturer, lectureDate);
    }

    public LectureResponse toDto(int capacity) {
        return new LectureResponse(
                this.title,
                this.lecturer,
                this.lectureDate,
                capacity
        );
    }
}
