package com.example.workflow.models;

public class TaskRep {
        

        private String id;
        private String name;
        private String processInstanceId;
        public TaskRep(String id2, String name2, String processInstanceId2) {
                this.name=name2;this.id=id2;this.processInstanceId=processInstanceId2;
        }
        // standard constructors
        // Getters
        public String getId() { return id;}
        public String getName() { return name;}
        public String getProcessInstanceId() { return processInstanceId;}
        
        // Setters
        public void setId(String id) { this.id = id;}
        public void setName(String newName) { this.name = newName;}
        public void setProcessInstanceId(String processInstanceId) { this.processInstanceId =processInstanceId; }
    
}
