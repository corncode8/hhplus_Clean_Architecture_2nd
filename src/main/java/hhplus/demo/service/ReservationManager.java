package hhplus.demo.service;


import hhplus.demo.domain.Reservation;
import hhplus.demo.domain.Student;
import hhplus.demo.dto.ReservationReq;
import hhplus.demo.repository.lecture.LectureCoreRepository;
import hhplus.demo.repository.reservation.ReservationCoreRepository;
import hhplus.demo.repository.student.StudentCoreRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class ReservationManager {

    private final ReservationCoreRepository reservationCoreRepository;
    private final StudentCoreRepository studentCoreRepository;
    private final LectureCoreRepository lectureCoreRepository;


    public Reservation regist (ReservationReq reservationReq) {
        return reservationCoreRepository.regist(reservationReq);
    }

    public Student find(Long userId) {
        return studentCoreRepository.find(userId);
    }


    public int reservationCnt(Long lectureId) {
        return lectureCoreRepository.reservationCnt(lectureId);
    }

}
