package com.hanghae.clean_architecture.integration;

import com.hanghae.clean_architecture.domain.lecture.Lecture;
import com.hanghae.clean_architecture.domain.lecture.LectureManager;
import com.hanghae.clean_architecture.domain.reservation.service.ReservationServiceImpl;
import com.hanghae.clean_architecture.infrastructure.lecture.LectureManagerRepository;
import com.hanghae.clean_architecture.infrastructure.lecture.LectureRepository;
import com.hanghae.clean_architecture.infrastructure.reservation.ReservationRepository;
import com.hanghae.clean_architecture.interfaces.request.Reserve;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class ReservationServiceIntegrationTest {

    @Autowired
    private ReservationServiceImpl reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

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
        reservationRepository.deleteAll();
        lectureRepository.deleteAll();
        lectureManagerRepository.deleteAll();
    }

    @Test
    void 특강_신청_성공() {
        // given
        Long userId = 1L;
        Long lectureId = lectureManagerRepository.findAll().get(0).getLectureId();
        Reserve reserve = new Reserve(lectureId);

        // when
        Long result = reservationService.reserve(userId, reserve);

        // then
        assertThat(result).isEqualTo(lectureId);
    }

    @Test
    void 특강_신청_성공시_잔여_신청수_정상반영() {
        // given
        Long userId = 1L;
        Long lectureId = lectureManagerRepository.findAll().get(0).getLectureId();
        Reserve reserve = new Reserve(lectureId);

        // when
        reservationService.reserve(userId, reserve);
        LectureManager result = lectureManagerRepository.getLectureManagerById(reserve.getLectureId());

        // then
        assertThat(result.getCapacity()).isEqualTo(1L);
    }
}