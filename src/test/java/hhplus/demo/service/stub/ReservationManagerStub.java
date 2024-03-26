package hhplus.demo.service.stub;

import hhplus.demo.common.Status;
import hhplus.demo.domain.Lecture;
import hhplus.demo.domain.Reservation;
import hhplus.demo.domain.Student;
import hhplus.demo.dto.ReservationReq;
import hhplus.demo.service.ReservationManager;

import java.util.HashMap;
import java.util.Map;

public class ReservationManagerStub extends ReservationManager {

    private Map<Long, Student> students = new HashMap<>();
    private Map<Long, Lecture> lectures = new HashMap<>();

    public ReservationManagerStub() {
        super(null,null,null);
    }

    @Override
    public Reservation regist(ReservationReq reservationReq) {
        Student student = students.getOrDefault(reservationReq.getStudentId(), new Student(reservationReq.studentId, Status.FAIL));
        Lecture lecture = lectures.getOrDefault(reservationReq.lectureId, new Lecture(reservationReq.lectureId, "항해 플러스"));

        Reservation reservation = Reservation.builder()
                .student(student)
                .lecture(lecture)
                .build();

        reservation.setSuccess();

        return reservation;
    }

    @Override
    public Student find(Long studentId) {
        return students.getOrDefault(studentId, new Student(studentId, Status.FAIL));
    }

    @Override
    public int reservationCnt(Long lectureId) {
        Lecture lecture = lectures.get(lectureId);

        return lecture.getReservationCnt();
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
