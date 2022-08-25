package com.bridgelabz.candidatebatchproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Entity
@Table(name = "CandidateTable")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateModel {

    @Id
    @Column(name = "candidateId")
    private Long id;
    private String cicId;
    private String fullName;
    private String email;
    private String mobileNo;
    private String hireDate;
    private String degree;
    private Double aggPer;
    private String city;
    private String state;
    private String preferredJobLocation;
    private String passOutYear;
    private String creatorUser;
    private String candidateStatus;


}
