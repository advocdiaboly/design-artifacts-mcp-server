package com.verter.bridge.service;

import com.verter.bridge.model.ADR;
import com.verter.bridge.model.Project;
import com.verter.bridge.repository.ADRRepository;
import com.verter.bridge.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ADRRepository adrRepository;

    @McpTool(name = "create_project", description = "Create a new project")
    public Project createProject(
            @McpToolParam(description = "name", required = true) String name,
            @McpToolParam(description = "description", required = true) String description) throws IOException {
        String id = UUID.randomUUID().toString();
        Project project = Project.builder()
                .id(id)
                .name(name)
                .description(description)
                .createdAt(LocalDateTime.now())
                .build();
        return projectRepository.save(project);
    }

    @McpTool(name = "list_projects", description = "List all available projects")
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @McpTool(name = "get_project", description = "Get details of a specific project")
    public Project getProject(@McpToolParam(description = "id", required = true) String id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @McpTool(name = "create_adr", description = "Create a new ADR")
    public ADR createADR(
            @McpToolParam(description = "projectId", required = true) String projectId,
            @McpToolParam(description = "title", required = true) String title,
            @McpToolParam(description = "content", required = true) String content) throws IOException {
        getProject(projectId); // Verify project exists
        String id = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        return adrRepository.save(projectId, title, content, id, now);
    }

    @McpTool(name = "list_adrs", description = "List ADRs for a project")
    public List<ADR> getADRsForProject(@McpToolParam(description = "projectId", required = true) String projectId) {
        getProject(projectId); // Verify project exists
        return adrRepository.findByProjectId(projectId);
    }

    @McpTool(name = "get_adr", description = "Get details of an ADR")
    public ADR getADR(@McpToolParam(description = "adrId", required = true) String adrId) {
        return adrRepository.findById(adrId)
                .orElseThrow(() -> new RuntimeException("ADR not found"));
    }

    @McpTool(name = "list_files", description = "List files in a directory for discovery")
    public List<String> listFiles(@McpToolParam(description = "path", required = false) String pathStr) throws IOException {
        Path path = (pathStr == null || pathStr.isBlank()) ? Paths.get(".") : Paths.get(pathStr);
        if (!Files.exists(path)) throw new NoSuchFileException(pathStr);
        try (Stream<Path> stream = Files.list(path)) {
            return stream.map(p -> p.getFileName().toString()).collect(Collectors.toList());
        }
    }

    @McpTool(name = "read_file", description = "Read the content of a file")
    public String readFile(@McpToolParam(description = "path", required = true) String pathStr) throws IOException {
        Path path = Paths.get(pathStr);
        if (!Files.exists(path)) throw new NoSuchFileException(pathStr);
        return Files.readString(path);
    }
}
