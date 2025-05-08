package com.management.yuvro.jpa.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Module {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long moduleId;
	private String moduleName;
	private String description;
	private boolean isModuleLocked;

	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "module")
	private List<Topic> topics;
}
