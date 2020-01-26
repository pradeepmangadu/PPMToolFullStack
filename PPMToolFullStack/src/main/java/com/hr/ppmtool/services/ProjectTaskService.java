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

		if (projectTask.getPriority() == 0 || projectTask.getPriority() == null) { // In the future we need to add this
																					// condition
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
		// Make Sure we are searching for existing project
		Backlog backlog = backlogRespository.findByProjectIdentifier(backlog_id);
		if (backlog == null) {
			throw new ProjectNotFoundException("Project with ID:'" + backlog_id + "' doesn't exists");
		}
		// Make Sure that Project Task Exists
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
		if (projectTask == null) {
			throw new ProjectNotFoundException("ProjectTask with SequenceID:'" + pt_id + "' doesn't exists");
		}
		// Make sure that backlog Id /ProjectId corresponds to the project task
		if (!projectTask.getProjectId().equals(backlog_id)) {
			throw new ProjectNotFoundException(
					"Project Task '" + pt_id + "' does not exists in project: '" + backlog_id);
		}
		return projectTask;
	}

	public ProjectTask updateByProjectSequence(ProjectTask updatedtTask, String backlog_id, String pt_id) {
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);
		projectTask = updatedtTask;

		return projectTaskRepository.save(projectTask);
	}

	public void deletebyProjectSequence(String backlog_id, String pt_id) {
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);
		projectTaskRepository.delete(projectTask);
	}
}
