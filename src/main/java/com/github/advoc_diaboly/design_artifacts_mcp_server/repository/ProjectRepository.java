package com.github.advoc_diaboly.design_artifacts_mcp_server.repository;

import com.github.advoc_diaboly.design_artifacts_mcp_server.model.Project;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
@Slf4j
public class ProjectRepository {

    private final String projectsBaseDir;

    public ProjectRepository(@Value("${project.base-dir}") String projectsBaseDir) {
        this.projectsBaseDir = projectsBaseDir;
    }

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(Paths.get(projectsBaseDir));
    }

    public Project save(Project project) throws IOException {
        Path projectPath = Paths.get(projectsBaseDir, project.getId());
        Files.createDirectories(projectPath);
        Files.createDirectories(projectPath.resolve("adrs"));

        Path readmePath = projectPath.resolve("README.md");
        if (!Files.exists(readmePath)) {
            String content = String.format("# %s\n\n%s\n", project.getName(), project.getDescription());
            Files.writeString(readmePath, content);
        }
        return project;
    }

    public List<Project> findAll() {
        try (Stream<Path> stream = Files.list(Paths.get(projectsBaseDir))) {
            return stream.filter(Files::isDirectory)
                    .map(this::loadProject)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Error listing projects", e);
            return Collections.emptyList();
        }
    }

    public Optional<Project> findById(String id) {
        Path projectPath = Paths.get(projectsBaseDir, id);
        if (!Files.exists(projectPath)) {
            return Optional.empty();
        }
        return Optional.ofNullable(loadProject(projectPath));
    }

    private Project loadProject(Path projectPath) {
        Path readmePath = projectPath.resolve("README.md");
        if (!Files.exists(readmePath)) return null;

        try {
            List<String> lines = Files.readAllLines(readmePath);
            String name = "Unknown";
            String description = "";

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.startsWith("# ")) {
                    name = line.substring(2).trim();
                    if (i + 1 < lines.size()) {
                        description = String.join("\n", lines.subList(i + 1, lines.size())).trim();
                    }
                    break;
                }
            }

            BasicFileAttributes attrs = Files.readAttributes(projectPath, BasicFileAttributes.class);
            LocalDateTime createdAt = LocalDateTime.ofInstant(attrs.creationTime().toInstant(), ZoneId.systemDefault());

            return Project.builder()
                    .id(projectPath.getFileName().toString())
                    .name(name)
                    .description(description)
                    .createdAt(createdAt)
                    .build();
        } catch (IOException e) {
            log.error("Error loading project from {}", readmePath, e);
            return null;
        }
    }
}
