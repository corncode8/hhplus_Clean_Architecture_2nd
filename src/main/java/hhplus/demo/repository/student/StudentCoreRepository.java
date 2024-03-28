package hhplus.demo.repository.student;

import hhplus.demo.domain.Student;

import java.util.Optional;

public interface StudentCoreRepository {
    Student findStudent(Long userId);
}
