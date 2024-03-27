package hhplus.demo.service.component;

import hhplus.demo.common.exceptions.BaseException;
import hhplus.demo.domain.Student;
import hhplus.demo.repository.student.StudentCoreRepository;
import hhplus.demo.repository.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static hhplus.demo.common.response.BaseResponseStatus.*;

@Component
@RequiredArgsConstructor
public class StudentReader implements StudentCoreRepository {

    private final StudentRepository studentRepository;

    @Override
    public Student find(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new BaseException(NOT_FIND_USER));
    }


}
