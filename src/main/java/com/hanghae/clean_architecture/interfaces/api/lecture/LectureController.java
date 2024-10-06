package com.hanghae.clean_architecture.interfaces.api.lecture;

import com.hanghae.clean_architecture.domain.lecture.service.LectureService;
import com.hanghae.clean_architecture.interfaces.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RequestMapping("/lecture")
@RestController
public class LectureController {

    private final LectureService lectureService;

    /**
     * 날짜별 특강 목록 조회 API
     */
    @GetMapping
    public ResponseEntity<?> searchLectures(
            @RequestParam LocalDateTime lectureStartDate,
            @RequestParam LocalDateTime lectureEndDate
    ) {
        return ResponseEntity.ok(new ResponseDto<>("목록을 조회했습니다.", lectureService.searchLectures(lectureStartDate, lectureEndDate)));
    }
}
