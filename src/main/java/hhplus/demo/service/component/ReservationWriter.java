package hhplus.demo.service.component;

import hhplus.demo.common.exceptions.BaseException;
import hhplus.demo.domain.Lecture;
import hhplus.demo.domain.Reservation;
import hhplus.demo.domain.Student;
import hhplus.demo.dto.ReservationReq;
import hhplus.demo.repository.reservation.ReservationCoreRepository;
import hhplus.demo.repository.reservation.ReservationRepository;
import hhplus.demo.repository.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import static hhplus.demo.common.response.BaseResponseStatus.*;

@Component
@RequiredArgsConstructor
public class ReservationWriter implements ReservationCoreRepository {

    private final StudentRepository studentRepository;
    private final ReservationRepository repository;
    private final LectureReader lectureReader;

    @Override
    public Reservation regist(ReservationReq reservationReq) {
        Student student = getStudent(reservationReq.studentId);

        Lecture lecture = getLecture(reservationReq.lectureId);


        Reservation reservation = Reservation.builder()
                .student(student)
                .lecture(lecture)
                .build();
        lecture.addReservation(reservation);
        lecture.reduceQuantity();

        return repository.save(reservation);
    }

    private Student getStudent(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
    }

    private Lecture getLecture(Long lectureId) {
        return lectureReader.findLectureById(lectureId)
                .orElseThrow(() -> new BaseException(NOT_FIND_LECTURE));
    }

}
