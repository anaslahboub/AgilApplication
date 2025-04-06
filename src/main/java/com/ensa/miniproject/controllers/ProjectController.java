package com.ensa.miniproject.controllers;

import com.ensa.miniproject.DTO.ProjectDTO;
import com.ensa.miniproject.services.project.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(projectService.addProject(projectDTO));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DEVELOPER', 'SCRUM_MASTER', 'PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        return ResponseEntity.ok(projectService.getProjects());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DEVELOPER', 'SCRUM_MASTER', 'PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<ProjectDTO> updateProject(
            @PathVariable Long id,
            @RequestBody ProjectDTO projectDTO) {
        return ResponseEntity.ok(projectService.updateProject(projectDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}