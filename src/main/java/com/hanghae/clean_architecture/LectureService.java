package com.hanghae.clean_architecture;

import java.util.List;

public interface LectureService {

    List<LectureResponse> searchLectures(LectureSearch lectureSearch);
}
