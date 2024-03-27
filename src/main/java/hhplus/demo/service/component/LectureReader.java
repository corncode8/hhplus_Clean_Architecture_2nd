package hhplus.demo.service.component;

import hhplus.demo.common.exceptions.BaseException;
import hhplus.demo.domain.Lecture;
import hhplus.demo.repository.lecture.LectureCoreRepository;
import hhplus.demo.repository.lecture.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static hhplus.demo.common.response.BaseResponseStatus.*;

@Component
@RequiredArgsConstructor
public class LectureReader implements LectureCoreRepository {

    private final LectureRepository lectureRepository;

    @Override
    public int reservationCnt(Long lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId)
                .orElseThrow(() -> new BaseException(NOT_FIND_LECTURE));

        return lecture.getReservations().size();
    }

    @Override
    public Optional<Lecture> findLectureById(Long id) {
        return lectureRepository.findLectureByIdWithLock(id);
    }

    @Override
    public Optional<Lecture> findLecture(Long id) {
        return lectureRepository.findById(id);
    }


}
