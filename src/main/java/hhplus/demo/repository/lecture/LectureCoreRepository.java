package hhplus.demo.repository.lecture;

import hhplus.demo.domain.Lecture;

import java.util.Optional;

public interface LectureCoreRepository {

    Lecture findLecture(Long id);
}
