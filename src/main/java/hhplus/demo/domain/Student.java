package hhplus.demo.domain;

import hhplus.demo.dto.Status;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static hhplus.demo.dto.Status.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studentId", nullable = false, updatable = false)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private hhplus.demo.dto.Status Status = FAIL;

    @OneToMany(mappedBy = "student", cascade = CascadeType.PERSIST)
    private List<Reservation> reservations = new ArrayList<>();

    public void setSuccess() {
        this.Status = SUCCESS;
    }
}
