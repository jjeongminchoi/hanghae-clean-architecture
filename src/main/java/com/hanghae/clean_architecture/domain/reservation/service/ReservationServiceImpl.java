package com.hanghae.clean_architecture.domain.reservation.service;

import com.hanghae.clean_architecture.domain.lecture.Lecture;
import com.hanghae.clean_architecture.domain.lecture.LectureManager;
import com.hanghae.clean_architecture.domain.lecture.constant.CapacityStatus;
import com.hanghae.clean_architecture.domain.reservation.Reservation;
import com.hanghae.clean_architecture.infrastructure.lecture.LectureManagerRepository;
import com.hanghae.clean_architecture.infrastructure.lecture.LectureRepository;
import com.hanghae.clean_architecture.infrastructure.reservation.ReservationRepository;
import com.hanghae.clean_architecture.interfaces.request.reservation.Reserve;
import com.hanghae.clean_architecture.interfaces.response.reservation.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final LectureManagerRepository lectureManagerRepository;
    private final LectureRepository lectureRepository;

    @Transactional
    @Override
    public Long reserve(Long userId, Reserve reserve) {
        // 신청가능한 강의 정보 조회
        LectureManager lectureManager = lectureManagerRepository.getLectureManagerById(reserve.getLectureId());
        if (lectureManager.getCapacityStatus() == CapacityStatus.FULL) {
            throw new IllegalArgumentException("선택한 특강은 신청이 가득찼습니다.");
        }

        // 이미 신청하진 않았는지 확인
        boolean present = reservationRepository.findById(userId).isPresent();
        if (present) {
            throw new IllegalArgumentException("이미 신청한 특강입니다.");
        }

        // 특강 신청
        Reservation reservation = reservationRepository.save(Reservation.create(userId, reserve.getLectureId()));

        // 잔여 신청 수 업데이트
        lectureManager.setCapacity();

        return reservation.getId();
    }

    @Override
    public List<ReservationResponse> searchReservations(Long userId) {
        // 특강 신청 목록 조회
        List<Reservation> reservations = reservationRepository.searchReservations(userId);
        if (reservations.isEmpty()) {
            throw new IllegalArgumentException("신청한 특강이 없습니다.");
        }

        // 특강 조회 Response return
        return reservations.stream()
                .map(reservation -> {
                    Lecture lecture = lectureRepository.getLectureById(reservation.getLectureId());
                    return new ReservationResponse(lecture.getId(), lecture.getTitle(), lecture.getLecturer(), reservation.getReservationStatus());
                })
                .collect(Collectors.toList());
    }
}
