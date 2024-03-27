package hhplus.demo.controller.response;

import hhplus.demo.domain.Reservation;
import hhplus.demo.domain.Student;
import hhplus.demo.dto.FindRes;
import hhplus.demo.dto.ReservationRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResponseMapper {

    // 신청
    public ReservationRes toReservationRes(Reservation reservation) {

        return new ReservationRes(reservation.getStatus());
    }


    public FindRes toFindUserRes(Student student) {

        return new FindRes(student.getId(), student.getStatus());
    }
}
