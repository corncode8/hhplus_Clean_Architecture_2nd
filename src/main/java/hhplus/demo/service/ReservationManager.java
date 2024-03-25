package hhplus.demo.service;

import hhplus.demo.common.exceptions.BaseException;
import hhplus.demo.domain.Reservation;
import hhplus.demo.domain.Student;
import hhplus.demo.dto.FindReq;
import hhplus.demo.dto.ReservationReq;
import hhplus.demo.repository.lecture.LectureReader;
import hhplus.demo.repository.reservation.ReservationWriter;
import hhplus.demo.repository.student.StudentReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static hhplus.demo.common.response.BaseResponseStatus.NOT_FIND_USER;

@Component
@RequiredArgsConstructor
public class ReservationManager {

    private final ReservationWriter registWriter;
    private final StudentReader studentReader;
    private final LectureReader lectureReader;

    public Reservation regist (ReservationReq reservationReq) {
        return registWriter.regist(reservationReq);
    }

    public Student find(FindReq findReq) {
        return studentReader.find(findReq);
    }

    public int reservationCnt(Long lectureId) {
        return lectureReader.reservationCnt(lectureId);
    }

    public Student getStudent(Long studentId) {
        return studentReader.getStudent(studentId);
    }
}
