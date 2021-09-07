package com.project.cms.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.cms.model.Lecturer;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer,Integer>{

}

