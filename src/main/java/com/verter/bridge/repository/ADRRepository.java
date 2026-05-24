package com.verter.bridge.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.verter.bridge.model.ADR;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
@Slf4j
public class ADRRepository {

    private final String projectsBaseDir;
    private final YAMLMapper yamlMapper = new YAMLMapper();
    private final Pattern frontmatterPattern = Pattern.compile("^---\\s*\\n(.*?)\\n---\\s*\\n(.*)$", Pattern.DOTALL);

    public ADRRepository(@Value("${project.base-dir}") String projectsBaseDir) {
        this.projectsBaseDir = projectsBaseDir;
    }

    public ADR save(String projectId, String title, String content, String adrId, LocalDateTime createdAt) throws IOException {
        String sanitizedTitle = title.replaceAll("[^a-zA-Z0-9]", "_");
        Path adrPath = Paths.get(projectsBaseDir, projectId, "adrs", sanitizedTitle + ".md");
        
        String frontmatter = String.format("---\nid: %s\nprojectId: %s\ntitle: %s\ncreatedAt: %s\n---\n\n%s",
                adrId, projectId, title, createdAt.format(DateTimeFormatter.ISO_DATE_TIME), content);

        Files.writeString(adrPath, frontmatter, StandardCharsets.UTF_8);
        
        return ADR.builder()
                .id(adrId)
                .projectId(projectId)
                .title(title)
                .content(content)
                .createdAt(createdAt)
                .build();
    }

    public List<ADR> findByProjectId(String projectId) {
        Path adrsDir = Paths.get(projectsBaseDir, projectId, "adrs");
        if (!Files.exists(adrsDir)) return Collections.emptyList();

        try (Stream<Path> stream = Files.list(adrsDir)) {
            return stream.filter(p -> p.toString().endsWith(".md"))
                    .map(this::loadADR)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("Error listing ADRs for project {}", projectId, e);
            return Collections.emptyList();
        }
    }

    public Optional<ADR> findById(String adrId) {
        try (Stream<Path> stream = Files.walk(Paths.get(projectsBaseDir))) {
            Optional<Path> adrPath = stream.filter(p -> p.toString().endsWith(".md"))
                    .filter(p -> {
                        ADR adr = loadADR(p);
                        return adr != null && adr.getId().equals(adrId);
                    })
                    .findFirst();

            if (adrPath.isPresent()) {
                return Optional.ofNullable(loadADR(adrPath.get()));
            }
        } catch (IOException e) {
            log.error("Error searching for ADR {}", adrId, e);
        }
        return Optional.empty();
    }

    private ADR loadADR(Path adrPath) {
        try {
            String content = Files.readString(adrPath);
            Matcher matcher = frontmatterPattern.matcher(content);
            if (matcher.find()) {
                String yaml = matcher.group(1);
                String body = matcher.group(2).trim();

                Map<String, Object> metadata = yamlMapper.readValue(yaml, Map.class);

                return ADR.builder()
                        .id((String) metadata.get("id"))
                        .projectId((String) metadata.get("projectId"))
                        .title((String) metadata.get("title"))
                        .createdAt(LocalDateTime.parse((String) metadata.get("createdAt"), DateTimeFormatter.ISO_DATE_TIME))
                        .content(body)
                        .build();
            }
        } catch (Exception e) {
            log.error("Error loading ADR from {}", adrPath, e);
        }
        return null;
    }
}
