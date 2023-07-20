package com.example.workflow.controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// Importing required classes
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.example.workflow.models.TaskRep;
import com.example.workflow.services.ProcessService;
  
// Annotation
@Controller
// Main class
public class ProcessController {
   
  @Autowired
  ProcessService processService;

 

    @RequestMapping("/hello")
    @ResponseBody
    public String helloGFG()
    {
        return "Hello GeeksForGeeks";
    }
  
  @PostMapping("/start")
  public  ResponseEntity<String> startProcess(){//@RequestBody StartProcessRequest request) {
   return this.processService.startProcess();
  }

  @GetMapping("/{processInstanceId}/tasks")
  public @ResponseBody List<TaskRep> getTasks(@PathVariable String processInstanceId) {
    
    return this.processService.getTasks(processInstanceId);
} 

  @PostMapping("/{processInstanceId}/complete-task/{taskId}")
  public ResponseEntity<String> completeTask(@PathVariable String processInstanceId, @PathVariable String taskId
                                          ,   @RequestBody Map<String, Object> taskVariables)
                                              {
    return this.processService.completeTask( processInstanceId, taskId,taskVariables);
  }



}

