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
			project.setProjectId(project.getProjectId().toUpperCase());
			return projectRepository.save(project);
		}
		catch(Exception e) {
			throw new ProjectIdException("Project ID '"+project.getProjectId().toUpperCase()+"' Already Exists");
		}
		
	}
	
	public Project findProjectByIdentifier(String projectId) {
		
		Project project =projectRepository.findByprojectId(projectId.toUpperCase());
		if(project == null) {
			throw new ProjectIdException("Project ID '"+ projectId+"' does not exist");
		}
		return project;
	}

	public Iterable<Project> findAllProjects(){
		return projectRepository.findAll();
		}
}
