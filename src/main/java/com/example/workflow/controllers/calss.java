package com.example.workflow.controllers;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/processes")
public class calss {

  private final RuntimeService runtimeService;
  private final TaskService taskService;

  @Autowired
  public calss(RuntimeService runtimeService, TaskService taskService) {
    this.runtimeService = runtimeService;
    this.taskService = taskService;
  }



  @PostMapping("/start")
  public ResponseEntity<String> startProcess(@RequestBody StartProcessRequest request) {
    try {
      String processInstanceId = runtimeService.startProcessInstanceByKey(request.getProcessKey(), request.getVariables())
          .getId();
      return ResponseEntity.ok("Process started with ID: " + processInstanceId);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to start process: " + e.getMessage());
    }
  }

 

  @PostMapping("/{processInstanceId}/complete-task/{taskId}")
  public ResponseEntity<String> completeTask(@PathVariable String processInstanceId, @PathVariable String taskId,
                                             @RequestBody Map<String, Object> taskVariables) {
    try {
      Task task = taskService.createTaskQuery().taskId(taskId).processInstanceId(processInstanceId).singleResult();
      if (task == null) {
        return ResponseEntity.badRequest().body("Task not found");
      }

      taskService.complete(taskId, taskVariables);

      return ResponseEntity.ok("Task completed successfully");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to complete task: " + e.getMessage());
    }
  }
}

