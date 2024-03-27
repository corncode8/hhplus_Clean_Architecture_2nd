package hhplus.demo.reservation.fake;

import hhplus.demo.common.Status;
import hhplus.demo.domain.Lecture;
import hhplus.demo.domain.Reservation;
import hhplus.demo.domain.Student;
import hhplus.demo.dto.ReservationReq;
import hhplus.demo.repository.reservation.ReservationCoreRepository;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class FakeReservationCoreRepository implements ReservationCoreRepository {

    private final Map<Long, Reservation> reservations = new HashMap<>();
    private final AtomicLong Generator = new AtomicLong();


    @Override
    public Reservation regist(ReservationReq reservationReq) {
        // ID 생성 + 예약 객체 생성
        Long reservationId = Generator.incrementAndGet();
        Reservation reservation = Reservation.builder()
                .student(new Student(reservationReq.studentId, Status.FAIL))
                .lecture(new Lecture(reservationReq.lectureId, "항해 플러스", LocalDateTime.now()))
                .build();

        // 메모리에 예약 객체 save
        reservations.put(reservationId, reservation);

        return reservation;
    }

    // 테스트 검증을 메소드 -> 저장된 예약 객체 가져오기
    public Reservation findReservationById(Long id) {
        return reservations.get(id);
    }
}
