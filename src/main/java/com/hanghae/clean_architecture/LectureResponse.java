package com.hanghae.clean_architecture;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LectureResponse {

    private String title;

    private String lecturer;

    private LocalDateTime lectureDate;

    private int capacity;

    public LectureResponse(String title, String lecturer, LocalDateTime lectureDate, int capacity) {
        this.title = title;
        this.lecturer = lecturer;
        this.lectureDate = lectureDate;
        this.capacity = capacity;
    }
}
