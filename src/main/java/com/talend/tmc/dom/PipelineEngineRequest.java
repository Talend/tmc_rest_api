package com.talend.tmc.dom;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class PipelineEngineRequest {
    @JsonInclude(JsonInclude.Include.NON_NULL)
//  @JsonProperty(access = JsonProperty.Access.READ_WRITE)
  private String name;
  @JsonInclude(JsonInclude.Include.NON_NULL)
//  @JsonProperty(access = JsonProperty.Access.READ_WRITE)
  private String environmentId;
  @JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonProperty(access = JsonProperty.Access.READ_WRITE)
  private String workspaceId;
  @JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonProperty(access = JsonProperty.Access.READ_WRITE)
  private String description;
}
