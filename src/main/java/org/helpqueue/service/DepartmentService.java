package org.helpqueue.service;


import lombok.extern.slf4j.Slf4j;
import org.helpqueue.domain.Department;
import org.helpqueue.domain.Ticket;
import org.helpqueue.exception.IdNotFoundException;
import org.helpqueue.persistence.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(long id) {
        return departmentRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
    }

    public Department saveADepartment(Department department) {
        return departmentRepository.save(department);
    }

    // Set tickets belonging to this department as null, leaving them orphaned.
    public void deleteDepartmentById(long id) {
        Department departmentList = this.getDepartmentById(id);

        for (Ticket ticket : departmentList.getTicketList()) {
            ticket.setDepartment(null);
        }

        departmentRepository.deleteById(id);
    }

    public Department updateADepartment(Department department) {
        return departmentRepository.save(department);
    }


}
