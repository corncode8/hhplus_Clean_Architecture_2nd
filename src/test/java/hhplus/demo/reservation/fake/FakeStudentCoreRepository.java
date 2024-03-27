package hhplus.demo.reservation.fake;

import hhplus.demo.domain.Student;
import hhplus.demo.repository.student.StudentCoreRepository;

import java.util.HashMap;
import java.util.Map;


public class FakeStudentCoreRepository implements StudentCoreRepository {

    private final Map<Long, Student> students = new HashMap<>();

    public void addStudent(Student student) {
        students.put(student.getId(), student);
    }

    @Override
    public Student find(Long id) {
        return students.get(id);
    }
}
