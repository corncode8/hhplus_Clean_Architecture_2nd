package hhplus.demo.service;


import hhplus.demo.common.Status;
import hhplus.demo.common.exceptions.BaseException;
import hhplus.demo.domain.Lecture;

import hhplus.demo.domain.Reservation;
import hhplus.demo.domain.Student;
import hhplus.demo.dto.ReservationReq;

import hhplus.demo.service.component.LectureReader;
import hhplus.demo.service.component.ReservationWriter;
import hhplus.demo.service.component.StudentReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.*;

import static hhplus.demo.common.response.BaseResponseStatus.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    @Mock
    private Clock clock;
    @Mock
    private  ReservationWriter reservationWriter;
    @Mock
    private  StudentReader studentReader;
    @Mock
    private  LectureReader lectureReader;
    @InjectMocks
    ReservationServiceImpl reservationServiceImpl;


    private final static LocalDateTime LOCAL_DATE = LocalDateTime.of(2024,4,20,14,00);
    private final static LocalDateTime FAIL_DATE = LocalDateTime.of(2024,4,20,12,00);


    @BeforeEach
    void setUp() {
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());
        when(clock.instant()).thenReturn(Instant.now());

        reservationServiceImpl = new ReservationServiceImpl(reservationWriter, studentReader, lectureReader, clock);
    }

    @DisplayName("regist 성공 테스트")
    @Test
    void registTest() {
        //given

        // 시간 설정 (4월 20일 1시 이후)
        when(clock.instant()).thenReturn(LOCAL_DATE.atZone(ZoneId.systemDefault()).toInstant());
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());

        Long studentId = 1L;
        Lecture lecture = Lecture.builder()
                .name("항해 플러스")
                .lectureAt(LocalDateTime.now())
                .quantity(30)
                .build();
        Student student = Student.builder()
                .id(studentId)
                .status(Status.FAIL)
                .build();
        Reservation reservation = Reservation.builder()
                .lecture(lecture)
                .student(student)
                .build();

        ReservationReq reservationReq = new ReservationReq(studentId, lecture.getId());

        when(studentReader.findStudent(reservation.getStudent().getId())).thenReturn(student);
        when(lectureReader.findLecture(reservation.getLecture().getId())).thenReturn(lecture);
        when(reservationWriter.regist(reservationReq)).thenReturn(reservation);


        //when
        Reservation result = reservationServiceImpl.regist(reservationReq);

        //then
        assertNotNull(result);
        assertEquals(Status.SUCCESS, result.getStatus());

    }

    @DisplayName("regist 실패 테스트")
    @Test
    void regist_실패_테스트_4월20일1시_이전() {
        //given

        // 시간 설정 (4월 20일 1시 이전)
        when(clock.instant()).thenReturn(FAIL_DATE.atZone(ZoneId.systemDefault()).toInstant());
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());

        Long studentId = 1L;
        Lecture lecture = Lecture.builder()
                .name("항해 플러스")
                .lectureAt(LocalDateTime.now())
                .quantity(30)
                .build();
        Student student = Student.builder()
                .id(studentId)
                .status(Status.FAIL)
                .build();

        ReservationReq reservationReq = new ReservationReq(studentId, lecture.getId());

        //when & then

        // "신청 가능 시간이 아닙니다." Exception 메세지 return
        BaseException baseException = assertThrows(BaseException.class, () -> reservationServiceImpl.regist(reservationReq));

        assertEquals(BEFORE_RESERVATION_TIME, baseException.getStatus());

    }

    @DisplayName("regist 실패 테스트 (이미 신청한 유저)")
    @Test
    void regist_실패_테스트_이미_신청한_유저() {
        //given

        // 시간 설정 (4월 20일 1시 이후)
        when(clock.instant()).thenReturn(LOCAL_DATE.atZone(ZoneId.systemDefault()).toInstant());
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());

        Long studentId = 1L;
        Long lectureId = 1L;
        Lecture lecture = new Lecture(lectureId, "항해플러스", LocalDateTime.now(), 30);
        Student student = Student.builder()
                .id(studentId)
                .status(Status.SUCCESS)
                .build();
        Reservation reservation = Reservation.builder()
                .lecture(lecture)
                .student(student)
                .build();

        ReservationReq reservationReq = new ReservationReq(studentId, lectureId);

        when(studentReader.findStudent(studentId)).thenReturn(student);
        when(lectureReader.findLecture(lectureId)).thenReturn(lecture);
        when(reservationWriter.regist(reservationReq)).thenReturn(reservation);

        //when & then

        // "이미 신청한 유저입니다." Exception 메세지 return
        BaseException baseException = assertThrows(BaseException.class, () -> reservationServiceImpl.regist(reservationReq));

        assertEquals(ALREADY_REGIST_USER, baseException.getStatus());

    }

    @DisplayName("regist 실패 테스트 (30명이 초과되었는지 여부)")
    @Test
    void registFailTest2() {
        //given
        // 시간 설정 (4월 20일 1시 이후)
        when(clock.instant()).thenReturn(LOCAL_DATE.atZone(ZoneId.systemDefault()).toInstant());
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());

        Long studentId = 1L;
        Long lectureId = 1L;

        // lecture의 quantity를 0으로 설정
        Lecture lecture = new Lecture(lectureId, "항해플러스", LocalDateTime.now(), 0);
        Student student = Student.builder()
                .id(studentId)
                .status(Status.SUCCESS)
                .build();
        Reservation reservation = Reservation.builder()
                .lecture(lecture)
                .student(student)
                .build();

        ReservationReq reservationReq = new ReservationReq(studentId, lectureId);

        when(lectureReader.findLecture(lectureId)).thenReturn(lecture);
        when(reservationWriter.regist(reservationReq)).thenReturn(reservation);


        //when & then

        // "강의가 품절입니다." Exception 메세지 return
        BaseException baseException = assertThrows(BaseException.class, () -> reservationServiceImpl.regist(reservationReq));

        assertEquals(EMPTY_QUANTITY_LECTURE, baseException.getStatus());

    }
}
