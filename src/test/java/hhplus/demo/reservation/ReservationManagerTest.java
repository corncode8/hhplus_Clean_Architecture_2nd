package hhplus.demo.reservation;

import hhplus.demo.common.Status;
import hhplus.demo.domain.Lecture;
import hhplus.demo.domain.Reservation;
import hhplus.demo.domain.Student;
import hhplus.demo.dto.ReservationReq;
import hhplus.demo.reservation.stub.LectureCoreRepositoryStub;
import hhplus.demo.reservation.stub.ReservationCoreRepositoryStub;
import hhplus.demo.reservation.stub.StudentCoreRepositoryStub;
import hhplus.demo.service.ReservationManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ReservationManagerTest {

    private StudentCoreRepositoryStub studentStub;
    private LectureCoreRepositoryStub lectureStub;
    private ReservationCoreRepositoryStub reservationStub;
    private ReservationManager reservationManager;

    @BeforeEach
    void setUp() {
        studentStub = new StudentCoreRepositoryStub();
        lectureStub = new LectureCoreRepositoryStub();
        reservationStub = new ReservationCoreRepositoryStub();
        reservationManager = new ReservationManager(reservationStub, studentStub, lectureStub);
    }


    @DisplayName("regist 테스트")
    @Test
    void registTest() {
        //given
        Long studentId = 1L;
        Long lectureId = 1L;
        Student student = new Student(studentId, Status.FAIL);
        Lecture lecture = new Lecture(lectureId, "항해 플러스");

        studentStub.addStudent(student);
        lectureStub.addLecture(lecture);

        ReservationReq reservationReq = new ReservationReq(studentId, lectureId);

        //when

        Reservation regist = reservationManager.regist(reservationReq);

        //then
        assertNotNull(regist);
        assertEquals(studentId, regist.getStudent().getId());
        assertEquals("항해 플러스", regist.getLecture().getName());
    }

    @DisplayName("find 테스트")
    @Test
    void findTest() {
        //given
        Long studentId = 1L;

        Student student = new Student(studentId, Status.FAIL);
        studentStub.addStudent(student);

        //when
        Student result = reservationManager.find(studentId);

        //then
        assertNotNull(result);
        assertEquals(studentId, result.getId());
    }

    @DisplayName("reservationCnt 테스트")
    @Test
    void reservationCntTest() {
        //given
        Long lectureId = 1L;
        Long studerntId = 1L;

        Student student = new Student(studerntId, Status.FAIL);
        Lecture lecture = new Lecture(lectureId, "항해 플러스");

        Reservation reservation = Reservation.builder()
                .student(student)
                .lecture(lecture)
                .build();


        lectureStub.addLecture(lecture);
        lectureStub.addReservation(lecture, reservation);

        //when
        int result = reservationManager.reservationCnt(lectureId);
//        System.out.println("result = " + result);

        //then
        assertNotNull(result);
        assertEquals(result, lecture.getReservations().size());
    }


}
