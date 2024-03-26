package hhplus.demo.repository.lecture;

import hhplus.demo.common.exceptions.BaseException;
import hhplus.demo.domain.Lecture;
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


}
