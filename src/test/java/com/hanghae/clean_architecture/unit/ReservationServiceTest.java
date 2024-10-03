package com.hanghae.clean_architecture.unit;

import com.hanghae.clean_architecture.domain.lecture.LectureManager;
import com.hanghae.clean_architecture.domain.reservation.Reservation;
import com.hanghae.clean_architecture.domain.reservation.service.ReservationServiceImpl;
import com.hanghae.clean_architecture.infrastructure.lecture.LectureManagerRepository;
import com.hanghae.clean_architecture.infrastructure.reservation.ReservationRepository;
import com.hanghae.clean_architecture.interfaces.request.Reserve;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private LectureManagerRepository lectureManagerRepository;

    // 생성자에서 id를 받지 않기 때문에 리플랙션으로 테스트용 id를 넣어준다.
    private void lectureMangerSetId(LectureManager lectureManager, Long id) {
        try {
            Field field = LectureManager.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(lectureManager, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void reservationSetId(Reservation reservation, Long id) {
        try {
            Field field = Reservation.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(reservation, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void 특강_신청_성공() {
        // given
        Long userId = 1L;
        Long lectureId = 10L;
        Long lectureManagerId = 10L;
        Long reservationId = 1L;
        Reserve reserve = new Reserve(lectureId);

        LectureManager lectureManager = LectureManager.create(lectureId);
        lectureMangerSetId(lectureManager, lectureManagerId);

        Reservation reservation = Reservation.create(userId, lectureId);
        reservationSetId(reservation, reservationId);

        // stub
        when(lectureManagerRepository.getLectureManagerById(reserve.getLectureId())).thenReturn(lectureManager);
        when(reservationRepository.findById(userId)).thenReturn(Optional.empty());
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        // when
        Long result = reservationService.reserve(userId, reserve);

        // then
        assertThat(result).isEqualTo(1L);
    }

    @Test
    void 특강_신청시_신청_가능한_잔여수가_없으면_예외발생() {
        // given
        Long userId = 1L;
        Long lectureId = 10L;
        Long lectureManagerId = 10L;
        Long reservationId = 1L;
        int MAX_RESERVATION_COUNT = 30;
        Reserve reserve = new Reserve(lectureId);

        LectureManager lectureManager = LectureManager.create(lectureId);
        lectureMangerSetId(lectureManager, lectureManagerId);

        // capacity FULL setting...
        for (int i = 0; i < MAX_RESERVATION_COUNT; i++) {
            lectureManager.setCapacity();
        }

        // stub
        when(lectureManagerRepository.getLectureManagerById(reserve.getLectureId())).thenReturn(lectureManager);

        // exception
        assertThatThrownBy(() -> reservationService.reserve(userId, reserve))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("선택한 특강은 신청이 가득찼습니다.");
    }

    @Test
    void 특강_신청시_이미_신청한_특강이면_예외발생() {
        // given
        Long userId = 1L;
        Long lectureId = 10L;
        Long lectureManagerId = 10L;
        Long reservationId = 1L;
        int MAX_RESERVATION_COUNT = 30;
        Reserve reserve = new Reserve(lectureId);

        LectureManager lectureManager = LectureManager.create(lectureId);
        lectureMangerSetId(lectureManager, lectureManagerId);

        Reservation reservation = Reservation.create(userId, lectureId);
        reservationSetId(reservation, reservationId);

        // stub
        when(lectureManagerRepository.getLectureManagerById(reserve.getLectureId())).thenReturn(lectureManager);
        when(reservationRepository.findById(userId)).thenReturn(Optional.of(reservation));

        // exception
        assertThatThrownBy(() -> reservationService.reserve(userId, reserve))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이미 신청한 특강입니다.");
    }

    @Test
    void 특강_신청_성공시_잔여_신청수_정상반영() {
        // given
        Long userId = 1L;
        Long lectureId = 10L;
        Long lectureManagerId = 10L;
        Long reservationId = 1L;
        Reserve reserve = new Reserve(lectureId);

        LectureManager lectureManager = LectureManager.create(lectureId);
        lectureMangerSetId(lectureManager, lectureManagerId);

        Reservation reservation = Reservation.create(userId, lectureId);
        reservationSetId(reservation, reservationId);

        // stub
        when(lectureManagerRepository.getLectureManagerById(reserve.getLectureId())).thenReturn(lectureManager);
        when(reservationRepository.findById(userId)).thenReturn(Optional.empty());
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        // when
        reservationService.reserve(userId, reserve);

        // then
        assertThat(lectureManager.getCapacity()).isEqualTo(1);
    }
}
