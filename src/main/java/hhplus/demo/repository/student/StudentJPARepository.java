package hhplus.demo.repository.student;

import hhplus.demo.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentJPARepository extends JpaRepository<Student, Long> {
    Optional<Student> findById(Long id);
}
