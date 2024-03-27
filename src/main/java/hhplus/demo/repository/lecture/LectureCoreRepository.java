package hhplus.demo.repository.lecture;

import hhplus.demo.domain.Lecture;

import java.util.Optional;

public interface LectureCoreRepository {

    int reservationCnt(Long lectureId);
    Optional<Lecture> findLectureById(Long id);

    Optional<Lecture> findLecture(Long id);
}
