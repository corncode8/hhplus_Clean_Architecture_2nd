package hhplus.demo.domain;

import hhplus.demo.common.Status;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static hhplus.demo.common.Status.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studentId", nullable = false, updatable = false)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Status status = FAIL;

    @OneToMany(mappedBy = "student")
    private List<Reservation> reservations = new ArrayList<>();

    public void setSuccess() {
        this.status = SUCCESS;
    }

    @Builder
    public Student(Long id, Status status) {
        this.id = id;
        this.status = status;
    }
}
