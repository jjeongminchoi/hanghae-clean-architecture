package com.hanghae.clean_architecture.domain.reservation.service;

import com.hanghae.clean_architecture.domain.lecture.LectureManager;
import com.hanghae.clean_architecture.domain.lecture.constant.CapacityStatus;
import com.hanghae.clean_architecture.domain.reservation.Reservation;
import com.hanghae.clean_architecture.infrastructure.lecture.LectureManagerRepository;
import com.hanghae.clean_architecture.infrastructure.reservation.ReservationRepository;
import com.hanghae.clean_architecture.interfaces.request.Reserve;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final LectureManagerRepository lectureManagerRepository;

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
}
