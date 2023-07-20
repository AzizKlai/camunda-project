package com.example.workflow.controllers;

import java.util.Map;

public class StartProcessRequest {
  private String processKey;
  private Map<String, Object> variables;

  public String getProcessKey() {
    return processKey;
  }

  public void setProcessKey(String processKey) {
    this.processKey = processKey;
  }

  public Map<String, Object> getVariables() {
    return variables;
  }

  public void setVariables(Map<String, Object> variables) {
    this.variables = variables;
  }
}