package com.hanghae.clean_architecture;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class LectureManager {

    private final int MAX_RESERVATION_COUNT = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_manager_id")
    private Long id;

    @Column(name = "lecture_id")
    private Long lectureId;

    private int capacity;

    private CapacityStatus capacityStatus;

    private LectureManager(Long lectureId) {
        this.lectureId = lectureId;
        this.capacity = 0;
        this.capacityStatus = CapacityStatus.EMPTY;
    }

    public static LectureManager create(Long lectureId) {
        return new LectureManager(lectureId);
    }
}
