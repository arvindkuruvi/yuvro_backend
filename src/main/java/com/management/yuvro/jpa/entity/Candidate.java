package com.management.yuvro.jpa.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
	private String institution;

	@JsonIgnore
	@ManyToMany(mappedBy = "candidates")
	private Set<Batch> batches;
}
