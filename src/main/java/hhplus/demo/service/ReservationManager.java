package hhplus.demo.service;

import hhplus.demo.common.exceptions.BaseException;
import hhplus.demo.domain.Lecture;
import hhplus.demo.domain.Reservation;
import hhplus.demo.domain.Student;
import hhplus.demo.dto.ReservationReq;
import hhplus.demo.repository.lecture.LectureCoreRepository;
import hhplus.demo.repository.reservation.ReservationCoreRepository;
import hhplus.demo.repository.student.StudentCoreRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static hhplus.demo.common.response.BaseResponseStatus.NOT_FIND_LECTURE;


@Component
@RequiredArgsConstructor
public class ReservationManager {

    private final ReservationCoreRepository reservationCoreRepository;
    private final StudentCoreRepository studentCoreRepository;
    private final LectureCoreRepository lectureCoreRepository;


    public Reservation regist (ReservationReq reservationReq) {
        return reservationCoreRepository.regist(reservationReq);
    }

    public Student findStudent(Long userId) {
        return studentCoreRepository.find(userId);
    }

    public Lecture findLecture(Long id) {
        return lectureCoreRepository.findLecture(id)
                .orElseThrow(() -> new BaseException(NOT_FIND_LECTURE));
    }


    public int reservationCnt(Long lectureId) {
        return lectureCoreRepository.reservationCnt(lectureId);
    }

}
