package com.hanghae.clean_architecture.domain.lecture.service;

import com.hanghae.clean_architecture.interfaces.response.lecture.LectureResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface LectureService {

    List<LectureResponse> searchLectures(LocalDateTime lectureStartDate, LocalDateTime lectureEndDate);
}
