package hhplus.demo.repository.student;

import hhplus.demo.common.exceptions.BaseException;
import hhplus.demo.domain.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static hhplus.demo.common.response.BaseResponseStatus.*;

@Component
@RequiredArgsConstructor
public class StudentReader implements StudentCoreRepository {

    private StudentRepository studentRepository;

    @Override
    public Student find(Long userId) {
        return studentRepository.findById(userId)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
    }


}
