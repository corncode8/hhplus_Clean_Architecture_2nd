package hhplus.demo;

import hhplus.demo.common.Status;
import hhplus.demo.common.exceptions.BaseException;
import hhplus.demo.domain.Lecture;
import hhplus.demo.domain.Student;
import hhplus.demo.dto.ReservationReq;
import hhplus.demo.repository.lecture.LectureRepository;
import hhplus.demo.repository.student.StudentRepository;
import hhplus.demo.service.ReservationService;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class IntegrationTest {
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private StudentRepository studentRepository;

    @MockBean
    private Clock clock;

    private Logger log = Logger.getLogger(IntegrationTest.class);

    // 동시성 테스트
    // 선착순 30명 동시 신청 -> 100명이 동시에 신청했을시 30명만 성공
    @DisplayName("선착순 테스트 30명 동시 신청")
    @Test
    void 선착순_동시성_테스트() {
        // "2024-04-20 14:00" 시간 설정
        LocalDateTime testDateTime = LocalDateTime.of(2024, 4, 20, 14, 0);
        Instant testInstant = testDateTime.toInstant(ZoneOffset.UTC);
        when(clock.instant()).thenReturn(testInstant);
        when(clock.getZone()).thenReturn(ZoneOffset.UTC);

        //given
        Lecture lecture = Lecture.builder()
                .name("항해 플러스")
                .lectureAt(LocalDateTime.now())
                .quantity(30)
                .build();
        lectureRepository.save(lecture);

        ExecutorService executorService = Executors.newFixedThreadPool(100);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        AtomicInteger successCnt = new AtomicInteger(0);
        AtomicInteger failCnt = new AtomicInteger(0);

        for (int i = 0; i < 100; i++) {
            final Long userId = i + 1L;
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    ReservationReq reservationReq = new ReservationReq(userId, lecture.getId());
                    reservationService.regist(reservationReq);
                    successCnt.incrementAndGet();
                } catch (BaseException e) {
                    log.error("BaseException e : {}", e);
                    failCnt.incrementAndGet();
                }
            }, executorService);
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executorService.shutdown();


        // 100번 요청시 특강 신청 성공 30건, 실패 70건
        assertEquals(30, successCnt.get());
        assertEquals(70, failCnt.get());
    }

    // 1번 user가 5번 신청하고 2번 user가 5번 신청
    // 총 10번 요청 성공 2번 실패 8번
    @DisplayName("중복유저 특강 신청 테스트")
    @Test
    void 중복유저_신청_테스트() {
        //given
        Lecture lecture = Lecture.builder()
                .name("항해 플러스2")
                .lectureAt(LocalDateTime.now())
                .quantity(2)
                .build();
        lectureRepository.save(lecture);

        ExecutorService executorService = Executors.newFixedThreadPool(100);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        AtomicInteger successCnt = new AtomicInteger(0);
        AtomicInteger failCnt = new AtomicInteger(0);

        for (int i = 0; i < 10; i++) {
            final Long user1 = 1L;
            final Long user2 = 2L;
            final int cnt = i;
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {

                ReservationReq User1 = new ReservationReq(user1, lecture.getId());
                ReservationReq User2 = new ReservationReq(user2, lecture.getId());

                try {
                    if (cnt <= 5) {
                        reservationService.regist(User1);
                    } else {
                        reservationService.regist(User2);
                    }
                    successCnt.incrementAndGet();
                } catch (BaseException e) {
                    failCnt.incrementAndGet();
                }
            }, executorService);
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executorService.shutdown();

        // 10번 특강 신청시 성공 2건, 실패 8건
        assertEquals(2, successCnt.get());
        assertEquals(8, failCnt.get());
    }


    @DisplayName("테스트")
    @Test
    void test() {
        for(int i = 0; i < 100; i++) {
            Long userId = i + 1L;
            Student student = Student.builder()
                    .id(userId)
                    .status(Status.FAIL)
                    .build();
            studentRepository.save(student);
        }
    }

}
