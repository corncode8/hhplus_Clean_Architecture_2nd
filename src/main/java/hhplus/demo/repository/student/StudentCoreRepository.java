package hhplus.demo.repository.student;

import hhplus.demo.domain.Student;

public interface StudentCoreRepository {
    Student find(Long userId);
}
