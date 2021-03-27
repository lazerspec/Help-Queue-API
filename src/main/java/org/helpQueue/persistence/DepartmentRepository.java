package org.helpQueue.persistence;

import org.helpQueue.domain.Department;
import org.helpQueue.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query(value = "UPDATE qa.TICKET SET department_id = NULL WHERE department_id = ?1", nativeQuery = true)
    void findOrphanedTickets();


}
