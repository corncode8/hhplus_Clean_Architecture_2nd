package hhplus.demo.repository.student;

import hhplus.demo.domain.Student;
import hhplus.demo.dto.FindReq;

public interface StudentRepository {
    Student find(FindReq findReq);
}
