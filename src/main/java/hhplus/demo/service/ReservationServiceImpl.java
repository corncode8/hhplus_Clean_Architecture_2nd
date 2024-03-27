package hhplus.demo.service;

import hhplus.demo.common.exceptions.BaseException;
import hhplus.demo.domain.Lecture;
import hhplus.demo.domain.Reservation;
import hhplus.demo.domain.Student;
import hhplus.demo.dto.ReservationReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;

import static hhplus.demo.common.response.BaseResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements ReservationService{

    private final ReservationManager reservationManager;
    private final Clock clock;

    public Reservation regist(ReservationReq reservationReq) {

        Reservation regist = reservationManager.regist(reservationReq);

        if (validation(regist)) {
            regist.setSuccess();
            regist.getStudent().setSuccess();
        } else {
            regist.setFail();
        }

        return regist;

    }

    @Transactional(readOnly = true)
    public Student find(Long userId) {
        return reservationManager.findStudent(userId);
    }

    /*
     * 요구사항
     * 특정 userId 로 선착순으로 제공되는 특강을 신청하는 API 를 작성합니다.
     * 특강은 4월 20일 토요일 1시 에 열리며, 선착순 30명만 신청 가능합니다.
     * 이미 신청자가 30명이 초과되면 이후 신청자는 요청을 실패합니다.
     */

    private Boolean validation(Reservation reservation) {

        // 4월 20일 1시 이후 LocalDateTime.now()에 Clock DI
//        if (LocalDateTime.now(clock).isBefore(LocalDateTime.of(2024,4,20,13,00))) {
//            return false;
//        }

        // 30명이 초과되었는지 여부를 확인
        Lecture lecture = reservationManager.findLecture(reservation.getLecture().getId());

        if (lecture.getQuantity() > 30) {
            throw new BaseException(EMPTY_QUANTITY_LECTURE);
        }

        Student student = reservationManager.findStudent(reservation.getStudent().getId());

        // 신청한 유저인지 확인
        if (student.getStatus().equals("SUCCESS")) {
            throw new BaseException(ALREADY_REGIST_USER);
        }

        return true;
    }

}
