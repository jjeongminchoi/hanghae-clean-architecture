package com.hanghae.clean_architecture;

import com.hanghae.clean_architecture.domain.lecture.Lecture;
import com.hanghae.clean_architecture.domain.lecture.LectureManager;
import com.hanghae.clean_architecture.infrastructure.lecture.LectureRepository;
import com.hanghae.clean_architecture.domain.lecture.service.LectureServiceImpl;
import com.hanghae.clean_architecture.infrastructure.lecture.LectureManagerRepository;
import com.hanghae.clean_architecture.interfaces.response.lecture.LectureResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LectureServiceTest {

    @InjectMocks
    private LectureServiceImpl lectureService;

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private LectureManagerRepository lectureManagerRepository;

    // 생성자에서 id를 받지 않기 때문에 리플랙션으로 테스트용 id를 넣어준다.
    private void setId(Lecture lecture, Long id) {
        try {
            Field field = Lecture.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(lecture, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void 특강_목록_조회_성공() {
        // given
        Lecture lecture = Lecture.create("TDD", "허재코치", LocalDateTime.of(2024, 9, 1, 13, 0));
        Lecture lecture2 = Lecture.create("클린아키텍처", "허재코치", LocalDateTime.of(2024, 9, 2, 13, 0));
        // Reflection 으로 ID 설정
        setId(lecture, 1L);
        setId(lecture2, 2L);
        List<Lecture> lectures = List.of(lecture, lecture2);

        LocalDateTime lectureStartDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime lectureEndDate = LocalDateTime.of(2024, 12, 31, 0, 0);

        // stub
        when(lectureRepository.getLecturesByDate(lectureStartDate, lectureEndDate)).thenReturn(lectures);
        when(lectureManagerRepository.getLectureManagerById(1L)).thenReturn(LectureManager.create(lecture.getId()));
        when(lectureManagerRepository.getLectureManagerById(2L)).thenReturn(LectureManager.create(lecture2.getId()));

        // when
        List<LectureResponse> lectureResponses = lectureService.searchLectures(lectureStartDate, lectureEndDate);

        // then
        assertThat(lectureResponses.size()).isEqualTo(2);
    }

    @Test
    void 특강_목록_조회_내역이_없을_때_예외를_발생시킨다() {
        // given
        LocalDateTime lectureStartDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime lectureEndDate = LocalDateTime.of(2024, 12, 31, 0, 0);

        // stub
        when(lectureRepository.getLecturesByDate(lectureStartDate, lectureEndDate)).thenReturn(List.of());

        // exception
        assertThatThrownBy(() -> lectureService.searchLectures(lectureStartDate, lectureEndDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("조회된 특강이 없습니다.");
    }
}
