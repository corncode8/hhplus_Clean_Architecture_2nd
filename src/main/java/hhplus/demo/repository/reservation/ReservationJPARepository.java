package hhplus.demo.repository.reservation;


import hhplus.demo.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationJPARepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findById(Long id);
}
