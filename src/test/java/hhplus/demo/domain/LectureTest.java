package hhplus.demo.domain;

import hhplus.demo.common.Status;
import hhplus.demo.common.exceptions.BaseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static hhplus.demo.common.response.BaseResponseStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LectureTest {

    @DisplayName("reduceQuantity 테스트")
    @Test
    void reduceQuantityTest() {
        //given
        Long lectureId = 1L;
        Long lectureId2 = 2L;

        Lecture lecture = new Lecture(lectureId, "항해 플러스", LocalDateTime.now(), 30);
        Lecture newLecture = new Lecture(lectureId2, "항해 플러스", LocalDateTime.now(), 0);


        //when
        lecture.reduceQuantity();

        //then
        assertThat(lecture).isNotNull();
        assertThat(lecture.getQuantity()).isEqualTo(29);

        // "강의가 품절입니다." Exception 테스트
        BaseException baseException = assertThrows(BaseException.class, () -> newLecture.reduceQuantity());
        assertEquals(EMPTY_QUANTITY_LECTURE, baseException.getStatus());
    }

    @DisplayName("addReservation 테스트")
    @Test
    void addReservationTest() {
        //given
        Long lectureId = 1L;
        Long studentId = 1L;

        Student student = new Student(studentId, Status.FAIL);
        Lecture lecture = new Lecture(lectureId, "항해 플러스", LocalDateTime.now(), 30);


        Reservation reservation = Reservation.builder()
                .student(student)
                .lecture(lecture)
                .build();

        //when
        lecture.addReservation(reservation);

        //then
        assertThat(lecture).isNotNull();
        assertThat(lecture.getReservations().size()).isEqualTo(1);
    }
}
