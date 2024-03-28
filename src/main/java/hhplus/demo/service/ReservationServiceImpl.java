package hhplus.demo.service;

import hhplus.demo.domain.Lecture;
import hhplus.demo.domain.Reservation;
import hhplus.demo.domain.Student;
import hhplus.demo.dto.ReservationReq;
import hhplus.demo.service.component.LectureReader;
import hhplus.demo.service.component.ReservationWriter;
import hhplus.demo.service.component.StudentReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService{

    private final ReservationWriter reservationWriter;
    private final StudentReader studentReader;
    private final LectureReader lectureReader;
    private final Clock clock;

    @Transactional
    public Reservation regist(ReservationReq reservationReq) {
        return reservationWriter.regist(reservationReq);
    }

    @Transactional(readOnly = true)
    public Student findStudent(Long userId) {
        return studentReader.findStudent(userId);
    }

    @Transactional(readOnly = true)
    public Lecture findLecture(Long lectureId) {
        return lectureReader.findLecture(lectureId);
    }
}
