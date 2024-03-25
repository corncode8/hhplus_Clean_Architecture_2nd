package hhplus.demo.service;

import hhplus.demo.common.exceptions.BaseException;
import hhplus.demo.domain.Student;
import hhplus.demo.dto.FindReq;
import hhplus.demo.dto.FindRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static hhplus.demo.common.response.BaseResponseStatus.NOT_FIND_USER;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final ReservationManager reservationManager;

    @Transactional(readOnly = true)
    public FindRes find(FindReq findReq) {

        Student student = reservationManager.find(findReq);

        return new FindRes(student.getId(), student.getStatus());
    }
}
