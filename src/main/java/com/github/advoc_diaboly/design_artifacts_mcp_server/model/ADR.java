package com.github.advoc_diaboly.design_artifacts_mcp_server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ADR {
    private String id;
    private String projectId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
}
