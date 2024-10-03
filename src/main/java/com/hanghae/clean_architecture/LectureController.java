package com.hanghae.clean_architecture;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/lecture")
@RestController
public class LectureController {

    private final LectureService lectureService;

    /**
     * 날짜별 특강 목록 조회 API
     */
    public ResponseEntity<?> searchLectures(@RequestBody LectureSearch lectureSearch) {
        return ResponseEntity.ok(new ResponseDto<>("목록을 조회했습니다.", lectureService.searchLectures(lectureSearch)));
    }
}
