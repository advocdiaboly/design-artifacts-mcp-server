package com.github.advocdiaboly.design_artifacts_mcp_server.controller;

import com.github.advocdiaboly.design_artifacts_mcp_server.model.ADR;
import com.github.advocdiaboly.design_artifacts_mcp_server.model.Project;
import com.github.advocdiaboly.design_artifacts_mcp_server.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping("/projects")
    public Project createProject(@RequestParam String name, @RequestParam(required = false) String description) throws IOException {
        return projectService.createProject(name, description);
    }

    @GetMapping("/projects")
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/projects/{id}")
    public Project getProject(@PathVariable String id) {
        return projectService.getProject(id);
    }

    @PostMapping("/projects/{projectId}/adrs")
    public ADR createADR(@PathVariable String projectId, @RequestParam String title, @RequestBody String content) throws IOException {
        return projectService.createADR(projectId, title, content);
    }

    @GetMapping("/projects/{projectId}/adrs")
    public List<ADR> getProjectADRs(@PathVariable String projectId) {
        return projectService.getADRsForProject(projectId);
    }

    @GetMapping("/adrs/{adrId}")
    public ADR getADR(@PathVariable String adrId) {
        return projectService.getADR(adrId);
    }
}
