package com.hr.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.ppmtool.domain.Backlog;
import com.hr.ppmtool.domain.ProjectTask;
import com.hr.ppmtool.repositories.BacklogRespository;
import com.hr.ppmtool.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	BacklogRespository backlogRespository;

	@Autowired
	ProjectTaskRepository projectTaskRepository;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

		// PTs to be addded to a specific project,project !=null,BL exists
		Backlog backlog = backlogRespository.findByProjectIdentifier(projectIdentifier);
		// set BL to Project
		projectTask.setBacklog(backlog);
		// we want our project sequence to be like IDPRO-1 IDPRO-2
		Integer BacklogSequence = backlog.getPTSequence();
		// Update the BL SEQUENCE
		BacklogSequence++;

		backlog.setPTSequence(BacklogSequence);
		// Add Sequence to Project Task
		projectTask.setProjectSequence(backlog.getProjectIdentifier() + "-" + BacklogSequence);
		projectTask.setProjectId(projectIdentifier);
		// Initial Priority when priority null

		if (projectTask.getPriority() == null) { // In the future we need to add this condition
													// projectTask.getPriority()==0||
			projectTask.setPriority(3);
		}

		// INITIAL status when status is null
		if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
			projectTask.setStatus("TO_DO");
		}
		return projectTaskRepository.save(projectTask);

	}
}
