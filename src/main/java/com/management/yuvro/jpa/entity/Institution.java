package com.management.yuvro.jpa.entity;

import java.util.List;

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
public class Institution {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long institutionId;
	private String institution;
	private String institutionCode;

	@ManyToMany(mappedBy = "institutions")
	private List<Batch> batches;
}
