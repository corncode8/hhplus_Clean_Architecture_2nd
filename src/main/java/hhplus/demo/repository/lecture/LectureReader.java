package hhplus.demo.repository.lecture;

import hhplus.demo.common.exceptions.BaseException;
import hhplus.demo.common.response.BaseResponseStatus;
import hhplus.demo.domain.Lecture;
import hhplus.demo.domain.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static hhplus.demo.common.response.BaseResponseStatus.*;

@Component
@RequiredArgsConstructor
public class LectureReader implements LectureRepository{

    private final LectureJPARepository lectureJPARepository;

    @Override
    public int reservationCnt(Long lectureId) {
        Lecture lecture = lectureJPARepository.findById(lectureId)
                .orElseThrow(() -> new BaseException(NOT_FIND_LECTURE));

        return lecture.getReservations().size();
    }

}
