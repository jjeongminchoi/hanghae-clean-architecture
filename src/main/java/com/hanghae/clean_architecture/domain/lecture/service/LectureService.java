package com.hanghae.clean_architecture.domain.lecture.service;

import com.hanghae.clean_architecture.interfaces.response.lecture.LectureResponse;
import com.hanghae.clean_architecture.interfaces.request.lecture.LectureSearch;

import java.util.List;

public interface LectureService {

    List<LectureResponse> searchLectures(LectureSearch lectureSearch);
}
