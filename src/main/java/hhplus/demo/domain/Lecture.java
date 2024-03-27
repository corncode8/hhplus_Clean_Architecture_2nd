package hhplus.demo.domain;

import hhplus.demo.common.exceptions.BaseException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static hhplus.demo.common.response.BaseResponseStatus.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lecture {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lectureId", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "lectureAt", nullable = false)
    private LocalDateTime lectureAt;

    @OneToMany(mappedBy = "lecture")
    private List<Reservation> reservations = new ArrayList<>();


    @Builder
    public Lecture(String name, LocalDateTime lectureAt, int quantity) {
        this.name = name;
        this.lectureAt = lectureAt;
        this.quantity = quantity;
    }

    public Lecture(Long id, String name, LocalDateTime lectureAt, int quantity) {
        this.id = id;
        this.name = name;
        this.lectureAt = lectureAt;
        this.quantity = quantity;
    }

    public Lecture(Long id, String name, LocalDateTime lectureAt) {
        this.id = id;
        this.name = name;
        this.lectureAt = lectureAt;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public int getReservationCnt() {
        return reservations.size();
    }

    public void reduceQuantity() {
        if (quantity -1 < 0) {
            throw new BaseException(EMPTY_QUANTITY_LECTURE);
        }
        quantity -= 1;
    }
}
