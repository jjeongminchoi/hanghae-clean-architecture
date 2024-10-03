package com.hanghae.clean_architecture.concurrent;

import com.hanghae.clean_architecture.domain.lecture.Lecture;
import com.hanghae.clean_architecture.domain.lecture.LectureManager;
import com.hanghae.clean_architecture.domain.reservation.service.ReservationServiceImpl;
import com.hanghae.clean_architecture.infrastructure.lecture.LectureManagerRepository;
import com.hanghae.clean_architecture.infrastructure.lecture.LectureRepository;
import com.hanghae.clean_architecture.infrastructure.reservation.ReservationRepository;
import com.hanghae.clean_architecture.interfaces.request.reservation.Reserve;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

//@Transactional
@SpringBootTest
public class ReservationConcurrentTest {

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

    @Test
    void 동시에_40명_특강신청시_30명만_성공해야한다() throws InterruptedException {
        // given
        Lecture lecture = lectureRepository.findAll().get(0);
        Reserve reserve = new Reserve(lecture.getId());

        int threadCount = 40;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            Long userId = (long) (i + 1);
            executorService.submit(() -> {
                try {
                    reservationService.reserve(userId, reserve);
                } finally {
                    latch.countDown(); // 스레드 작업이 끝나면 countDown한다.
                }
            });
        }

        // 모든 스레드가 작업을 완료할 때까지 대기
        latch.await();
        executorService.shutdown();

        // then
        assertThat(reservationRepository.findAll().size()).isEqualTo(30);
    }

    @Test
    void 동일한_사용자가_같은_특강을_5번_신청했을_때_1번만_성공해야한다() throws InterruptedException {
        // given
        Lecture lecture = lectureRepository.findAll().get(0);
        Reserve reserve = new Reserve(lecture.getId());

        int threadCount = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    reservationService.reserve(1L, reserve);
                } finally {
                    latch.countDown(); // 스레드 작업이 끝나면 countDown한다.
                }
            });
        }

        // 모든 스레드가 작업을 완료할 때까지 대기
        latch.await();
        executorService.shutdown();

        // then
        assertThat(reservationService.searchReservations(1L).size()).isEqualTo(1);
    }
}
