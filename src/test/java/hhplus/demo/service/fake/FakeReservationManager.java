package hhplus.demo.service.fake;

import hhplus.demo.common.Status;
import hhplus.demo.domain.Lecture;
import hhplus.demo.domain.Reservation;
import hhplus.demo.domain.Student;
import hhplus.demo.dto.ReservationReq;
import hhplus.demo.service.ReservationManager;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class FakeReservationManager extends ReservationManager {

    private Map<Long, Student> students = new HashMap<>();
    private Map<Long, Lecture> lectures = new HashMap<>();

    private Clock clock;


    public FakeReservationManager(Clock clock) {
        super(null,null,null);
        this.clock = clock;
    }

    @Override
    public Reservation regist(ReservationReq reservationReq) {
        Boolean isValid = validation(reservationReq.lectureId, reservationReq.studentId);

        Student student = students.getOrDefault(reservationReq.getStudentId(), new Student(reservationReq.studentId, Status.FAIL));
        Lecture lecture = lectures.getOrDefault(reservationReq.lectureId, new Lecture(reservationReq.lectureId, "항해 플러스", LocalDateTime.now()));

        Reservation reservation = Reservation.builder()
                .student(student)
                .lecture(lecture)
                .build();

        if (isValid) {

            reservation.setSuccess();
            reservation.getStudent().setSuccess();

            return reservation;
        } else {
            reservation.setFail();
            return reservation;
        }
    }

    @Override
    public Student findStudent(Long studentId) {
        return students.get(0L);
    }

    @Override
    public int reservationCnt(Long lectureId) {
        Lecture lecture = lectures.getOrDefault(0L, new Lecture(lectureId, "항해 플러스", LocalDateTime.now()));

        return lecture.getReservationCnt();
    }

    private Boolean validation(Long lectureId, Long studentId) {

        // 4월 20일 1시 이후 LocalDateTime.now()에 Clock DI
        if (LocalDateTime.now(clock).isBefore(LocalDateTime.of(2024,4,20,13,00))) {
            System.out.println("4월 20일 1시 이후");
            return false;
        }

        // 30명이 초과되었는지 여부를 확인
        long reservationCount = reservationCnt(lectureId);

        if (reservationCount > 30) {
            System.out.println("30명이 초과됨");
            return false;
        }

        Student student = findStudent(studentId);

        // 신청한 유저인지 확인
        if (student.getStatus().equals("SUCCESS")) {
            System.out.println("신청한 유저");
            return false;
        }
        System.out.println("return true");
        return true;
    }



    public void addReservation(Lecture lecture, Reservation reservation) {
        lecture.addReservation(reservation);
    }

    public void addStudent(Student student) {
        students.put(student.getId(), student);
    }
    public void addLecture(Lecture lecture) {
        lectures.put(lecture.getId(), lecture);
    }


}
