package hhplus.demo.controller;

import hhplus.demo.common.response.BaseResponse;
import hhplus.demo.controller.response.ResponseMapper;
import hhplus.demo.domain.Reservation;
import hhplus.demo.domain.Student;
import hhplus.demo.dto.FindRes;
import hhplus.demo.dto.ReservationReq;
import hhplus.demo.dto.ReservationRes;
import hhplus.demo.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final ResponseMapper responseMapper;

    @PostMapping("/regist")
    public BaseResponse<ReservationRes> Reservation(@RequestBody ReservationReq reservationReq) {

        Reservation regist = reservationService.regist(reservationReq);

        return new BaseResponse<>(responseMapper.toReservationRes(regist));

    }

    @GetMapping("/check/{id}")
    public BaseResponse<FindRes> getReservation(@PathVariable("userId")Long userId) {
        Student student = reservationService.findStudent(userId);

        return new BaseResponse<>(responseMapper.toFindUserRes(student));
    }
}
