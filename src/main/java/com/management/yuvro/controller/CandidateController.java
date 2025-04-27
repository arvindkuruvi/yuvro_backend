//package com.management.yuvro.controller;
//
//import com.management.yuvro.jpa.repository.CandidateRepository;
//
//@RestController
//@RequestMapping("/candidates")
//public class CandidateController {
//	 private final CandidateRepository candidateRepository;
//
//	    public CandidateController(CandidateRepository candidateRepository) {
//	        this.candidateRepository = candidateRepository;
//	    }
//
//	    @GetMapping("/{id}/batches")
//	    public Set<Department> getDepartmentsForStudent(@PathVariable Long id) {
//	        return candidateRepository.findById(id)
//	            .map(student -> student.getCourses().stream()
//	                .map(Course::getDepartment)
//	                .collect(Collectors.toSet()))
//	            .orElse(Collections.emptySet());
//	    }
//}
