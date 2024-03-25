package hhplus.demo.repository.student;

import hhplus.demo.common.exceptions.BaseException;
import hhplus.demo.domain.Student;
import hhplus.demo.dto.FindReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static hhplus.demo.common.response.BaseResponseStatus.*;

@Component
@RequiredArgsConstructor
public class StudentReader implements StudentRepository {

    private StudentJPARepository studentJPARepository;

    @Override
    public Student find(FindReq findReq) {
        return studentJPARepository.findById(findReq.getUserId())
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
    }

    public Student getStudent(Long studentId) {
        return studentJPARepository.findById(studentId)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
    }

}
