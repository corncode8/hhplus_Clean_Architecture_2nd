package hhplus.demo.service.component;

import hhplus.demo.common.exceptions.BaseException;
import hhplus.demo.domain.Lecture;
import hhplus.demo.repository.lecture.LectureCoreRepository;
import hhplus.demo.repository.lecture.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import static hhplus.demo.common.response.BaseResponseStatus.NOT_FIND_LECTURE;

@Component
@RequiredArgsConstructor
public class LectureReader implements LectureCoreRepository {

    private final LectureRepository lectureRepository;

    @Override
    public Lecture findLecture(Long id) {
        return lectureRepository.findById(id)
                .orElseThrow(() -> new BaseException(NOT_FIND_LECTURE));
    }


}
