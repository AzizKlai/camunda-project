package com.example.workflow.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// Importing required classes
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.camunda.bpm.engine.task.Task;

import com.example.workflow.models.TaskRep;
  
// Annotation
@org.springframework.stereotype.Service
// Main class
public class ProcessService {
    @Autowired
    private final RuntimeService runtimeService;
    @Autowired
    private final TaskService taskService;
     
    @Autowired
   public ProcessService(RuntimeService runtimeService,TaskService taskService) {
    this.runtimeService = runtimeService;
    this.taskService=taskService;
  }



  public ResponseEntity<String>  startProcess(){
    try {
        // String processInstanceId = runtimeService.startProcessInstanceByKey(request.getProcessKey(), request.getVariables())
        //     .getId();
        ProcessInstance processInstance=  runtimeService.startProcessInstanceByKey("demande-process");
   
        String processInstanceId= processInstance.getId(); 
   
         return ResponseEntity.ok("Process started with ID: " + processInstanceId+
         "\n Process started. Number of currently running"
         + "process instances = "
         + runtimeService.createProcessInstanceQuery().count());
   
       } catch (Exception e) {
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to start process: " + e.getMessage());
       }
  }


  public List<TaskRep> getTasks(String processInstanceId) {
 
    List<Task> usertasks = taskService.createTaskQuery()
      .processInstanceId(processInstanceId)
      .list();

    return usertasks.stream()
      .map(task -> new TaskRep(
      task.getId(), task.getName(), task.getProcessInstanceId()))
      .collect(Collectors.toList());
}

  public ResponseEntity<String> completeTask(String processInstanceId,String taskId,
                                            Map<String, Object> taskVariables) 
                                          {
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