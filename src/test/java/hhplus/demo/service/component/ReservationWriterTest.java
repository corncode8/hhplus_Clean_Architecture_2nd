package hhplus.demo.service.component;

import hhplus.demo.common.Status;
import hhplus.demo.common.exceptions.BaseException;
import hhplus.demo.domain.Lecture;
import hhplus.demo.domain.Reservation;
import hhplus.demo.domain.Student;
import hhplus.demo.dto.ReservationReq;
import hhplus.demo.repository.lecture.LectureRepository;
import hhplus.demo.repository.reservation.ReservationRepository;
import hhplus.demo.repository.student.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static hhplus.demo.common.response.BaseResponseStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationWriterTest {

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private ReservationRepository repository;
    @Mock
    private LectureRepository lectureRepository;
    @Mock
    private Clock clock;

    @InjectMocks
    private ReservationWriter reservationWriter;

    private final static LocalDateTime LOCAL_DATE = LocalDateTime.of(2024,4,20,14,00);

    @DisplayName("regist 테스트")
    @Test
    void registTest() {
        // 시간 설정 (4월 20일 1시 이후)
        when(clock.instant()).thenReturn(LOCAL_DATE.atZone(ZoneId.systemDefault()).toInstant());
        when(clock.getZone()).thenReturn(ZoneId.systemDefault());

        //given
        Long studentId = 1L;
        Long lectureId = 1L;
        Student student = new Student(studentId, Status.FAIL);
        Lecture lecture = new Lecture(lectureId, "항해 플러스", LocalDateTime.now(), 30);

        ReservationReq reservationReq = new ReservationReq(studentId, lectureId);

        Reservation reservation = Reservation.builder()
                .student(student)
                .lecture(lecture)
                .build();

        // lecture.addReservation(reservation)
        int size = lecture.getReservations().size();

        // lecture.reduceQuantity()
        int lectureQuantity = 0;

        if (lecture.getQuantity() - 1 > 0) {
            lectureQuantity = lecture.getQuantity();
            lectureQuantity -= 1;
        }

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(lectureRepository.findById(lectureId)).thenReturn(Optional.of(lecture));
        when(repository.save(any(Reservation.class))).thenReturn(reservation);

        //when
        Reservation result = reservationWriter.regist(reservationReq);

        //then
        assertNotNull(result);

        //lecture.reduceQuantity()
        assertEquals(29, lectureQuantity);

        // lecture.addReservation(reservation)
        assertEquals(1, size + 1);
    }
}
