package hhplus.demo.repository.student;

import hhplus.demo.domain.Student;


public interface StudentCoreRepository {
    Student findStudent(Long userId);
}
