package hhplus.demo.repository.reservation;

import hhplus.demo.common.exceptions.BaseException;
import hhplus.demo.domain.Lecture;
import hhplus.demo.domain.Reservation;
import hhplus.demo.domain.Student;
import hhplus.demo.dto.ReservationReq;
import hhplus.demo.repository.lecture.LectureJPARepository;
import hhplus.demo.repository.student.StudentJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import static hhplus.demo.common.response.BaseResponseStatus.*;

@Component
@RequiredArgsConstructor
public class ReservationWriter implements ReservationRepository {

    private final StudentJPARepository studentJPARepository;
    private final ReservationJPARepository repository;
    private final LectureJPARepository lectureJPARepository;

    @Override
    public Reservation regist(ReservationReq reservationReq) {
        Student student = getStudent(reservationReq.studentId);

        Lecture lecture = getLecture(reservationReq.lectureId);


        Reservation reservation = Reservation.builder()
                .student(student)
                .lecture(lecture)
                .build();

        return repository.save(reservation);
    }

    private Student getStudent(Long studentId) {
        return studentJPARepository.findById(studentId)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
    }

    private Lecture getLecture(Long lectureId) {
        return lectureJPARepository.findLectureByIdWithLock(lectureId)
                .orElseThrow(() -> new BaseException(NOT_FIND_LECTURE));
    }

}
