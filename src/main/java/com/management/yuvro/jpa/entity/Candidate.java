package com.management.yuvro.jpa.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Candidate {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long candidateId;
	private String name;
	private String phone;
	private String email;
	private String type;
	private String branch;

	@ManyToMany(mappedBy = "candidates")
	private List<Batch> batches;
	
	@OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Practice> candidatePractices;
}
