package hhplus.demo.service;


import hhplus.demo.common.Status;
import hhplus.demo.domain.Lecture;

import hhplus.demo.domain.Reservation;
import hhplus.demo.domain.Student;
import hhplus.demo.dto.ReservationReq;
import hhplus.demo.dto.ReservationRes;


import hhplus.demo.service.fake.FakeReservationManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    private FakeReservationManager fakeReservationManager;

    @Mock
    private Clock clock;
    @InjectMocks
    private ReservationService reservationService;

    private final static LocalDateTime LOCAL_DATE = LocalDateTime.of(2024,4,20,14,00);
    private final static LocalDateTime FAIL_DATE = LocalDateTime.of(2024,4,20,12,00);


    @BeforeEach
    void setUp() {
        Mockito.when(clock.getZone()).thenReturn(ZoneId.systemDefault());
        Mockito.when(clock.instant()).thenReturn(Instant.now());

        fakeReservationManager = new FakeReservationManager(clock);

        reservationService = new ReservationServiceImpl(fakeReservationManager, clock);
    }

    @DisplayName("regist 성공 테스트")
    @Test
    void registTest() {
        //given

        // 시간 설정 (4월 20일 1시 이후)
        Mockito.when(clock.instant()).thenReturn(LOCAL_DATE.atZone(ZoneId.systemDefault()).toInstant());
        Mockito.when(clock.getZone()).thenReturn(ZoneId.systemDefault());

        Long studentId = 1L;
        Long lectureId = 1L;
        Student student = new Student(studentId, Status.FAIL);
        Lecture lecture = new Lecture(lectureId, "항해 플러스", LocalDateTime.now());

        fakeReservationManager.addStudent(student);
        fakeReservationManager.addLecture(lecture);

        ReservationReq reservationReq = new ReservationReq(studentId, lectureId);

        //when
        Reservation result = reservationService.regist(reservationReq);

        //then
        assertNotNull(result);
        assertEquals(Status.SUCCESS, result.getStatus());

    }

    @DisplayName("regist 실패 테스트")
    @Test
    void regist_실패_테스트_4월20일1시_이전() {
        //given

        // 시간 설정 (4월 20일 1시 이전)
        Mockito.when(clock.instant()).thenReturn(FAIL_DATE.atZone(ZoneId.systemDefault()).toInstant());
        Mockito.when(clock.getZone()).thenReturn(ZoneId.systemDefault());

        Long studentId = 1L;
        Long lectureId = 1L;
        Student student = new Student(studentId, Status.FAIL);
        Lecture lecture = new Lecture(lectureId, "항해 플러스", LocalDateTime.now());

        fakeReservationManager.addStudent(student);
        fakeReservationManager.addLecture(lecture);

        ReservationReq reservationReq = new ReservationReq(studentId, lectureId);

        //when
        Reservation result = fakeReservationManager.regist(reservationReq);

        //then
        assertNotNull(result);
        assertEquals(Status.FAIL, result.getStatus());

    }

    @DisplayName("regist 실패 테스트 (이미 신청한 유저)")
    @Test
    void regist_실패_테스트_이미_신청한_유저() {
        //given

        // 시간 설정 (4월 20일 1시 이후)
        Mockito.when(clock.instant()).thenReturn(LOCAL_DATE.atZone(ZoneId.systemDefault()).toInstant());
        Mockito.when(clock.getZone()).thenReturn(ZoneId.systemDefault());

        Long studentId = 1L;
        Long lectureId = 1L;
        Student student = new Student(studentId, Status.SUCCESS);
        Lecture lecture = new Lecture(lectureId, "항해 플러스", LocalDateTime.now());
        fakeReservationManager.addStudent(student);
        fakeReservationManager.addLecture(lecture);

        ReservationReq reservationReq = new ReservationReq(studentId, lectureId);

        //when
        Reservation result = fakeReservationManager.regist(reservationReq);

        //then
        assertNotNull(result);
        assertEquals(Status.FAIL, result.getStatus());
    }

    @DisplayName("regist 실패 테스트 (30명이 초과되었는지 여부)")
    @Test
    void registFailTest2() {
        //given
        // 시간 설정 (4월 20일 1시 이후)
        Mockito.when(clock.instant()).thenReturn(LOCAL_DATE.atZone(ZoneId.systemDefault()).toInstant());
        Mockito.when(clock.getZone()).thenReturn(ZoneId.systemDefault());

        Long studentId = 1L;
        Long lectureId = 1L;
        Student student = new Student(studentId, Status.FAIL);
        Lecture lecture = new Lecture(lectureId, "항해 플러스", LocalDateTime.now());
        fakeReservationManager.addStudent(student);
        fakeReservationManager.addLecture(lecture);

        ReservationReq reservationReq = new ReservationReq(studentId, lectureId);

        // 30명 초과
        Mockito.when(fakeReservationManager.reservationCnt(lectureId)).thenReturn(31);

        //when
        Reservation result = fakeReservationManager.regist(reservationReq);

        //then
        assertEquals(Status.FAIL, result.getStatus());

    }


}
