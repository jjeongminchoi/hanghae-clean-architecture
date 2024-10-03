package com.hanghae.clean_architecture.integration;

import com.hanghae.clean_architecture.domain.lecture.Lecture;
import com.hanghae.clean_architecture.domain.lecture.LectureManager;
import com.hanghae.clean_architecture.infrastructure.lecture.LectureRepository;
import com.hanghae.clean_architecture.domain.lecture.service.LectureServiceImpl;
import com.hanghae.clean_architecture.infrastructure.lecture.LectureManagerRepository;
import com.hanghae.clean_architecture.interfaces.response.lecture.LectureResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class LectureServiceIntegrationTest {

    @Autowired
    private LectureServiceImpl lectureService;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private LectureManagerRepository lectureManagerRepository;

    @BeforeEach
    void setUp() {
        Lecture lecture = Lecture.create("TDD", "허재코치", LocalDateTime.of(2024, 9, 1, 13, 0));
        Lecture lecture2 = Lecture.create("클린아키텍처", "허재코치", LocalDateTime.of(2024, 9, 2, 13, 0));
        lectureRepository.save(lecture);
        lectureRepository.save(lecture2);
        lectureManagerRepository.save(LectureManager.create(lecture.getId()));
        lectureManagerRepository.save(LectureManager.create(lecture2.getId()));
    }

    @AfterEach
    void cleanUp() {
        lectureRepository.deleteAll();
        lectureManagerRepository.deleteAll();
    }

    @Test
    void 특강_목록_조회_성공() {
        // when
        LocalDateTime lectureStartDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime lectureEndDate = LocalDateTime.of(2024, 12, 31, 0, 0);
        List<LectureResponse> result = lectureService.searchLectures(lectureStartDate, lectureEndDate);

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getTitle()).isEqualTo("TDD");
        assertThat(result.get(0).getLecturer()).isEqualTo("허재코치");
        assertThat(result.get(1).getTitle()).isEqualTo("클린아키텍처");
        assertThat(result.get(1).getLecturer()).isEqualTo("허재코치");
    }

    @Test
    void 특강_목록_조회_내역이_없을_때_예외를_발생시킨다() {
        // given
        lectureRepository.deleteAll();
        lectureManagerRepository.deleteAll();
        LocalDateTime lectureStartDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime lectureEndDate = LocalDateTime.of(2024, 12, 31, 0, 0);

        // exception
        assertThatThrownBy(() -> lectureService.searchLectures(lectureStartDate, lectureEndDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("조회된 특강이 없습니다.");
    }
}