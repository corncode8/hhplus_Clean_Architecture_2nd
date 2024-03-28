package hhplus.demo.service.component;

import hhplus.demo.common.Status;
import hhplus.demo.common.exceptions.BaseException;
import hhplus.demo.domain.Lecture;
import hhplus.demo.domain.Reservation;
import hhplus.demo.domain.Student;
import hhplus.demo.dto.ReservationReq;
import hhplus.demo.repository.lecture.LectureRepository;
import hhplus.demo.repository.reservation.ReservationCoreRepository;
import hhplus.demo.repository.reservation.ReservationRepository;
import hhplus.demo.repository.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import java.time.Clock;
import java.time.LocalDateTime;

import static hhplus.demo.common.response.BaseResponseStatus.*;

@Component
@RequiredArgsConstructor
public class ReservationWriter implements ReservationCoreRepository {

    private final StudentRepository studentRepository;
    private final ReservationRepository repository;
    private final LectureRepository lectureRepository;

    private final Clock clock;

    @Override
    public Reservation regist(ReservationReq reservationReq) {
        Student student = getStudent(reservationReq.studentId);

        Lecture lecture = getLecture(reservationReq.lectureId);

        Reservation reservation = Reservation.builder()
                .student(student)
                .lecture(lecture)
                .build();

        if (validation(reservation)) {
            student.setSuccess();

            lecture.addReservation(reservation);
            lecture.reduceQuantity();

            reservation.setSuccess();

        } else {
            reservation.setFail();
        }

        return repository.save(reservation);
    }

    public Student getStudent(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
    }

    public Lecture getLecture(Long lectureId) {
        return lectureRepository.findLectureByIdWithLock(lectureId)
                .orElseThrow(() -> new BaseException(NOT_FIND_LECTURE));
    }

    /*
     * 요구사항
     * 특정 userId 로 선착순으로 제공되는 특강을 신청하는 API 를 작성합니다.
     * 특강은 4월 20일 토요일 1시 에 열리며, 선착순 30명만 신청 가능합니다.
     * 이미 신청자가 30명이 초과되면 이후 신청자는 요청을 실패합니다.
     */
    private Boolean validation(Reservation reservation) {

        // 4월 20일 1시 이후 LocalDateTime.now()에 Clock DI
        if (LocalDateTime.now(clock).isBefore(LocalDateTime.of(2024,4,20,13,00))) {
            throw new BaseException(BEFORE_RESERVATION_TIME);
        }

        Student student = getStudent(reservation.getStudent().getId());
        // 신청한 유저인지 확인
        if (student.getStatus().equals(Status.SUCCESS)) {
            throw new BaseException(ALREADY_REGIST_USER);
        }

        // 30명이 초과되었는지 여부를 확인
        Lecture lecture = getLecture(reservation.getLecture().getId());
        if (lecture.getQuantity() <= 0) {
            throw new BaseException(EMPTY_QUANTITY_LECTURE);
        }

        return true;
    }

}
