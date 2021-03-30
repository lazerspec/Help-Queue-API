package org.helpQueue.controller;


import lombok.extern.slf4j.Slf4j;
import org.helpQueue.domain.Department;
import org.helpQueue.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin("*")
@RequestMapping(value = "/api/v1/department")
public class DepartmentController {


    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<Department>> getAllDepartmentsWithTickets() {
        log.info("Obtaining all departments with tickets");
        return new ResponseEntity<>(departmentService.getAllDepartments(), HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable(name = "id") long id) {
        log.info("Getting department and it's tickets by id: {}", id);
        return new ResponseEntity<>(departmentService.getDepartmentById(id), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Department> saveADepartment(@RequestBody Department department) {
        // Possible stack overflow?
        log.info("Saving department: {}", department);
        return new ResponseEntity<>(departmentService.saveADepartment(department),HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Department> updateADepartment(@RequestBody Department department) {
        // Possible stack overflow?
        log.info("Updating department: {}", department);
        return new ResponseEntity<>(departmentService.updateADepartment(department),HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletaADepartment(@PathVariable(name = "id") long id) {
        departmentService.deleteDepartmentById(id);
        return ResponseEntity.noContent().build();
    }

}
