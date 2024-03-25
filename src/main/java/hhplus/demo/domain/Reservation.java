package hhplus.demo.domain;

import hhplus.demo.dto.Status;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static hhplus.demo.dto.Status.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registrationId", nullable = false, updatable = false)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Status Status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentId")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lectureId")
    private Lecture lecture;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder
    public Reservation(Student student, Lecture lecture) {
        this.student = student;
        this.lecture = lecture;
    }

    public void setSuccess() {
        this.Status = SUCCESS;
    }

    public void setFail() {
        this.Status = FAIL;
    }

    public void setCancle() {
        this.Status = CANCLE;
    }




}
