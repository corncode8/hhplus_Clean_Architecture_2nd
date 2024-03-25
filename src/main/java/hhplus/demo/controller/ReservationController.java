package hhplus.demo.controller;

import hhplus.demo.common.response.BaseResponse;
import hhplus.demo.dto.FindReq;
import hhplus.demo.dto.FindRes;
import hhplus.demo.dto.ReservationReq;
import hhplus.demo.dto.ReservationRes;
import hhplus.demo.service.ReservationService;
import hhplus.demo.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final StudentService studentService;

    @PostMapping("/regist")
    public BaseResponse<ReservationRes> Reservation(ReservationReq reservationReq) {

        ReservationRes regist = reservationService.regist(reservationReq);

        return new BaseResponse<>(regist);

    }

    @GetMapping("/check")
    public BaseResponse<FindRes> getReservation(FindReq findReq) {
        FindRes findRes = studentService.find(findReq);

        return new BaseResponse<>(findRes);
    }
}
