package hhplus.demo.service;


import hhplus.demo.common.Status;
import hhplus.demo.domain.Lecture;
import hhplus.demo.domain.Reservation;
import hhplus.demo.domain.Student;
import hhplus.demo.dto.ReservationReq;
import hhplus.demo.dto.ReservationRes;
import hhplus.demo.reservation.stub.LectureCoreRepositoryStub;
import hhplus.demo.reservation.stub.ReservationCoreRepositoryStub;
import hhplus.demo.reservation.stub.StudentCoreRepositoryStub;
import hhplus.demo.service.ReservationManager;
import hhplus.demo.service.ReservationService;
import hhplus.demo.service.stub.ReservationManagerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.Clock;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationServiceTest {

    private ReservationManagerStub reservationManagerStub;
    private StudentCoreRepositoryStub studentStub;
    private LectureCoreRepositoryStub lectureStub;
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        LocalDateTime testTime = LocalDateTime.of(2024, 4, 20, 14, 0);
        studentStub = new StudentCoreRepositoryStub();
        lectureStub = new LectureCoreRepositoryStub();
        reservationManagerStub = new ReservationManagerStub();
        reservationService = new ReservationService(reservationManagerStub);
    }

    @DisplayName("regist 테스트")
    @Test
    void registTest() {
        //given
        Long studentId = 1L;
        Long lectureId = 1L;
        Student student = new Student(studentId, Status.FAIL);
        Lecture lecture = new Lecture(lectureId, "항해 플러스");

        reservationManagerStub.addStudent(student);
        reservationManagerStub.addLecture(lecture);

        ReservationReq reservationReq = new ReservationReq(studentId, lectureId);

        //when
        ReservationRes regist = reservationService.regist(reservationReq);

        //then
        assertNotNull(regist);
        assertEquals(Status.SUCCESS, regist.getStatus());

    }

    @DisplayName("regist 실패 테스트 (이미 신청한 유저)")
    @Test
    void test() {
        //given
        Long studentId = 1L;
        Long lectureId = 1L;
        Student student = new Student(studentId, Status.SUCCESS);
        Lecture lecture = new Lecture(lectureId, "항해 플러스");
        reservationManagerStub.addStudent(student);
        reservationManagerStub.addLecture(lecture);



        ReservationReq reservationReq = new ReservationReq(studentId, lectureId);

        //when
        ReservationRes regist = reservationService.regist(reservationReq);

        //then
        assertNotNull(regist);
        assertEquals(Status.FAIL, regist.getStatus());
    }


    // TODO: 4월 20일 1시 이후, 30명이 초과되었는지 여부, 신청한 유저인지 확인 실패 테스트 추가 + 테스트 모두 STUB으로 변경해보기
}
