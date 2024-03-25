package hhplus.demo.repository.reservation;

import hhplus.demo.domain.Lecture;
import hhplus.demo.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface ReservationJPARepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findById(Long id);
}
