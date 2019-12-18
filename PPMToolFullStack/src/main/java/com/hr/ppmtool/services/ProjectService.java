package com.hr.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.ppmtool.domain.Project;
import com.hr.ppmtool.exceptions.ProjectIdException;
import com.hr.ppmtool.repositories.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	public Project saveOrUpdate(Project project) {
		
		try {
			project.setProject_Id(project.getProject_Id().toUpperCase());
			return projectRepository.save(project);
		}
		catch(Exception e) {
			throw new ProjectIdException("Project ID '"+project.getProject_Id().toUpperCase()+"' Already Exists");
		}
		
	}

}
