package com.hanghae.clean_architecture;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;
    private final LectureManagerRepository lectureManagerRepository;

    @Override
    public List<LectureResponse> searchLectures(LectureSearch lectureSearch) {
        // 특강 목록 조회
        List<Lecture> lectures = lectureRepository.getLecturesByDate(lectureSearch.getLectureStartDate(), lectureSearch.getLectureEndDate());
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
