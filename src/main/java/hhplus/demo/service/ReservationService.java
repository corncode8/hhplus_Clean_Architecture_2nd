package hhplus.demo.service;

import hhplus.demo.common.exceptions.BaseException;
import hhplus.demo.domain.Reservation;
import hhplus.demo.domain.Student;
import hhplus.demo.dto.ReservationReq;
import hhplus.demo.dto.ReservationRes;
import hhplus.demo.dto.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;

import static hhplus.demo.common.response.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationManager reservationManager;

    @Transactional
    public ReservationRes regist(ReservationReq reservationReq) {
        Boolean validation = validation(reservationReq.lectureId);

        try {
            ReservationRes reservationRes;
            if (validation) {
                Reservation regist = reservationManager.regist(reservationReq);
                Student student = reservationManager.getStudent(reservationReq.studentId);

                regist.setSuccess(); student.setSuccess();

                return new ReservationRes(regist.getStatus());

            } else {
                return reservationRes = new ReservationRes(Status.FAIL);
            }

        } catch (BaseException e) {
            throw new BaseException(FAIL_RESERVATION);
        }
    }

    /*
    * 요구사항
    * 특정 userId 로 선착순으로 제공되는 특강을 신청하는 API 를 작성합니다.
    * 특강은 4월 20일 토요일 1시 에 열리며, 선착순 30명만 신청 가능합니다.
    * 이미 신청자가 30명이 초과되면 이후 신청자는 요청을 실패합니다.
    */

    private Boolean validation(Long lectureId) {

        // 4월 20일 1시 이후
        if (LocalDateTime.now().isBefore(LocalDateTime.of(2024,4,20,13,00))) {
            return false;
        }

        // 30명이 초과되었는지 여부를 확인
        long reservationCount = reservationManager.reservationCnt(lectureId);
        if (reservationCount >= 30) {
            return false;
        }

        return true;
    }
}
