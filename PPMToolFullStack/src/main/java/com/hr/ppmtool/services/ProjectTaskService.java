package com.hr.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.ppmtool.domain.Backlog;
import com.hr.ppmtool.domain.Project;
import com.hr.ppmtool.domain.ProjectTask;
import com.hr.ppmtool.exceptions.ProjectNotFoundException;
import com.hr.ppmtool.repositories.BacklogRespository;
import com.hr.ppmtool.repositories.ProjectRepository;
import com.hr.ppmtool.repositories.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	BacklogRespository backlogRespository;

	@Autowired
	ProjectTaskRepository projectTaskRepository;

	@Autowired
	ProjectRepository projectRepository;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

		// PTs to be addded to a specific project,project !=null,BL exists
		try {
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
		} catch (Exception e) {
			throw new ProjectNotFoundException("Project Not Found");
		}

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

	public Iterable<ProjectTask> findBacklogById(String Id) {
		Project project = projectRepository.findByprojectId(Id);
		if (project == null) {
			throw new ProjectNotFoundException("Project with ID:'" + Id + "' doesn't exists");
		}

		return projectTaskRepository.findByProjectIdOrderByPriority(Id);
	}

	public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id) {
//make sure we are searching right projectTask
		return projectTaskRepository.findByProjectSequence(pt_id);
	}
}
