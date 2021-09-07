package com.project.cms.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.cms.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer>{

}

