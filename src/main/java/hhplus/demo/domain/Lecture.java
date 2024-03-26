package hhplus.demo.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lecture {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lectureId", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lectureAt", nullable = false)
    private LocalDateTime lectureAt;

    @OneToMany(mappedBy = "lecture")
    private List<Reservation> reservations = new ArrayList<>();


    @Builder
    public Lecture(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public int getReservationCnt() {
        return reservations.size();
    }
}
