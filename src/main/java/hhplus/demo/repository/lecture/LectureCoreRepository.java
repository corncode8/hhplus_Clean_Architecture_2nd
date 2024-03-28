package hhplus.demo.repository.lecture;

import hhplus.demo.domain.Lecture;


public interface LectureCoreRepository {

    Lecture findLecture(Long id);
}
