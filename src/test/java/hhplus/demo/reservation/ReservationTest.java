package hhplus.demo.reservation;

import hhplus.demo.domain.Lecture;
import hhplus.demo.domain.Reservation;
import hhplus.demo.domain.Student;
import hhplus.demo.dto.ReservationReq;
import hhplus.demo.common.Status;
import hhplus.demo.repository.lecture.LectureJPARepository;
import hhplus.demo.repository.reservation.ReservationJPARepository;
import hhplus.demo.repository.reservation.ReservationWriter;
import hhplus.demo.repository.student.StudentJPARepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReservationTest {

    @Mock
    StudentJPARepository studentJPARepository;

    @Mock
    ReservationJPARepository reservationJPARepository;
    @Mock
    LectureJPARepository lectureJPARepository;

    @InjectMocks
    ReservationWriter reservationWriter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("특강 신청 테스트")
    @Test
    void reservationTest() {
        //given
        Long studentId = 1L;
        Long lectureId = 1L;
        Student student = Student.builder()
                .id(studentId)
                .status(Status.FAIL)
                .build();
        Lecture lecture = Lecture.builder()
                .id(lectureId)
                .name("항해플러스")
                .build();
        ReservationReq reservationReq = new ReservationReq(studentId, lectureId);

        when(studentJPARepository.findById(studentId)).thenReturn(Optional.of(student));
        when(lectureJPARepository.findLectureByIdWithLock(lectureId)).thenReturn(Optional.of(lecture));
        when(reservationJPARepository.save(any(Reservation.class))).thenAnswer(i -> i.getArguments()[0]);

        // when
        Reservation result = reservationWriter.regist(reservationReq);

        // then
        assertNotNull(result);
        assertEquals(student, result.getStudent());
        assertEquals(lecture, result.getLecture());

        verify(studentJPARepository).findById(studentId);
        verify(lectureJPARepository).findLectureByIdWithLock(lectureId);
        verify(reservationJPARepository).save(any(Reservation.class));
    }
}
