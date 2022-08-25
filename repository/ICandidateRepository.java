package com.bridgelabz.candidatebatchproject.repository;

import com.bridgelabz.candidatebatchproject.model.CandidateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICandidateRepository extends JpaRepository<CandidateModel,Long> {
}
