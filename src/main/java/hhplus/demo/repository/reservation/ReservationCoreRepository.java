package hhplus.demo.repository.reservation;

import hhplus.demo.domain.Reservation;
import hhplus.demo.dto.ReservationReq;


public interface ReservationCoreRepository {
    Reservation regist(ReservationReq reservationReq);
}
