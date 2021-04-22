package org.helpqueue.controller;


import lombok.extern.slf4j.Slf4j;
import org.helpqueue.domain.Department;
import org.helpqueue.dto.DepartmentDTO;
import org.helpqueue.service.DepartmentService;
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
    public ResponseEntity<Department> saveADepartment(@RequestBody DepartmentDTO departmentDTO) {

        Department persistentDepartment = new Department();
        persistentDepartment.setDepartmentName(departmentDTO.getDepartmentName());
        persistentDepartment.setTicketList(departmentDTO.getTicketList());


        log.info("Saving department: {}", persistentDepartment);
        return new ResponseEntity<>(departmentService.saveADepartment(persistentDepartment),HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Department> updateADepartment(@RequestBody DepartmentDTO departmentDTO) {

        Department persistentDepartment = new Department();

        persistentDepartment.setDepartmentName(departmentDTO.getDepartmentName());
        persistentDepartment.setTicketList(departmentDTO.getTicketList());
        persistentDepartment.setId(departmentDTO.getId());

        log.info("Updating department: {}", persistentDepartment);
        return new ResponseEntity<>(departmentService.updateADepartment(persistentDepartment),HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteADepartment(@PathVariable(name = "id") long id) {
        log.info("Deleting department with Id: {}", id);
        departmentService.deleteDepartmentById(id);
        return ResponseEntity.noContent().build();
    }

}
