package com.talend.tmc.dom;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PipelineEngine {
    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String description;
    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String createDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String updateDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String runtimeId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String availability;
    @JsonInclude(JsonInclude.Include.NON_NULL)
//  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private boolean managed;
    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private WorkspaceInfo workspaceInfo;
    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String[] runProfiles;
    @JsonInclude(JsonInclude.Include.NON_NULL)
//  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String preAuthorizedKey;
    @JsonInclude(JsonInclude.Include.NON_NULL)
//  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private boolean cloudRunner;
}

@Data
class WorkspaceInfo {
    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
//  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
//  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String description;
//  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String owner;
    @JsonInclude(JsonInclude.Include.NON_NULL)
//  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    /* ['shared', 'personal', 'custom'], */
    private String type;
    @JsonInclude(JsonInclude.Include.NON_NULL)
//  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private EnvironmentInfo environmentInfo;
}

@Data
class EnvironmentInfo {
    @JsonInclude(JsonInclude.Include.NON_NULL)
//  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private String id;
  @JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private String name;
  @JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private String description;
  @JsonProperty("default")
  private boolean isDefault;
}
