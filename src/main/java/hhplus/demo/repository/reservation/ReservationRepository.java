package hhplus.demo.repository.reservation;

import hhplus.demo.domain.Reservation;
import hhplus.demo.dto.ReservationReq;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;

public interface ReservationRepository {
    Reservation regist(ReservationReq reservationReq);
}
