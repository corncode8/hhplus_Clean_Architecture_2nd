package hhplus.demo.service;


import hhplus.demo.common.Status;
import hhplus.demo.domain.Lecture;

import hhplus.demo.domain.Student;
import hhplus.demo.dto.ReservationReq;
import hhplus.demo.dto.ReservationRes;


import hhplus.demo.service.stub.ReservationManagerStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationServiceTest {

    @Mock
    private ReservationManager reservationManager;

    private ReservationManagerStub reservationManagerStub;
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationManagerStub = new ReservationManagerStub();
        reservationService = new ReservationService(reservationManagerStub);
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("regist 성공 테스트")
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
    void registFailTest() {
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

    @DisplayName("regist 실패 테스트 (30명이 초과되었는지 여부)")
    @Test
    void registFailTest2() {
        //given
        Long studentId = 1L;
        Long lectureId = 1L;
        Student student = new Student(studentId, Status.FAIL);
        Lecture lecture = new Lecture(lectureId, "항해 플러스");
        reservationManagerStub.addStudent(student);
        reservationManagerStub.addLecture(lecture);

        ReservationReq reservationReq = new ReservationReq(studentId, lectureId);

        // 30명 초과
        Mockito.when(reservationManager.reservationCnt(lectureId)).thenReturn(31);

        //when
        ReservationRes result = reservationService.regist(reservationReq);

        //then
        assertEquals(Status.FAIL, result.getStatus());

    }


}
