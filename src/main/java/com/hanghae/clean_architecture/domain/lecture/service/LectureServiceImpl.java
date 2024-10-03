package com.hanghae.clean_architecture.domain.lecture.service;

import com.hanghae.clean_architecture.infrastructure.lecture.LectureManagerRepository;
import com.hanghae.clean_architecture.domain.lecture.Lecture;
import com.hanghae.clean_architecture.domain.lecture.LectureManager;
import com.hanghae.clean_architecture.infrastructure.lecture.LectureRepository;
import com.hanghae.clean_architecture.interfaces.response.lecture.LectureResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;
    private final LectureManagerRepository lectureManagerRepository;

    @Override
    public List<LectureResponse> searchLectures(LocalDateTime lectureStartDate, LocalDateTime lectureEndDate) {
        // 특강 목록 조회
        List<Lecture> lectures = lectureRepository.getLecturesByDate(lectureStartDate, lectureEndDate);
        if (lectures.isEmpty()) {
            throw new IllegalArgumentException("조회된 특강이 없습니다.");
        }

        // 특강 조회 Response return
        return lectures.stream()
                .map(lecture -> {
                    // 특강 목록 잔여 수 조회
                    LectureManager lectureManager = lectureManagerRepository.getLectureManagerById(lecture.getId());
                    return lecture.toDto(lectureManager.getCapacity());
                })
                .collect(Collectors.toList());
    }
}
