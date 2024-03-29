package org.helpqueue.service;

import lombok.extern.slf4j.Slf4j;
import org.helpqueue.domain.Department;
import org.helpqueue.domain.Ticket;
import org.helpqueue.exception.IdNotFoundException;
import org.helpqueue.persistence.DepartmentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

@Slf4j
@ActiveProfiles(profiles = "test")
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(scripts = "classpath:data-test.sql")
public class DepartmentServiceIntTest {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private DepartmentRepository departmentRepository;


    @Before
    public void setup() {

    }

    @Test
    public void testGetDepartmentsFromDb() {
        List<Department> list = departmentService.getAllDepartments();

        assertThat(list.get(0).getDepartmentName(), is("Wealth"));
        assertThat(list.get(1).getDepartmentName(), is("CoreEng"));
        assertThat(list.get(2).getDepartmentName(), is("Trade"));
    }

    @Test
    public void testGetDepartmentById() {
        Department department = departmentService.getDepartmentById(1L);

        assertThat(department.getDepartmentName(), is("Wealth"));
        assertThat(department.getId(), is(1L));
        assertThat(department.getTicketList().size(), is(2));
    }

    @Test
    public void testGetDepartmentWithInvalidId() {
        assertThrows(IdNotFoundException.class, () -> {
            Department department = departmentService.getDepartmentById(5L);
        });
    }


    @Test
    @Transactional
    public void testCreateADepartmentWithNoTickets() {
        Department department = new Department();
        department.setDepartmentName("Engineering");

        Department savedDepartment = departmentService.saveADepartment(department);

        assertThat(savedDepartment.getDepartmentName(), is("Engineering"));
        assertThat(savedDepartment.getTicketList(), is(empty()));
    }

    @Test
    @Transactional
    public void testCreateDepartmentWithTickets() {
        Department newDepartment = new Department();
        newDepartment.setDepartmentName("WithTickets");

        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(new Ticket("Ticket1","Person A","A description","high","open"));
        newDepartment.setTicketList(ticketList);
        Department savedDepartment = departmentService.saveADepartment(newDepartment);

        List<Department> departmentList = departmentService.getAllDepartments();

        assertThat(savedDepartment.getDepartmentName(), is("WithTickets"));
        assertThat(savedDepartment.getId(), is(4L));
        assertThat(savedDepartment.getTicketList().size(), is(1));
    }

    @Test
    @Transactional
    public void testUpdateADepartment() {
        Department department = departmentService.getDepartmentById(1L);
        assertThat(department.getDepartmentName(), is("Wealth"));
        department.setDepartmentName("A New Department Name");

        Department updatedDepartment = departmentService.updateADepartment(department);

        assertThat(updatedDepartment.getId(), is(department.getId()));
        assertThat(updatedDepartment.getDepartmentName(), is("A New Department Name"));
        assertThat(updatedDepartment.getTicketList().size(), is(department.getTicketList().size()));
    }

    @Test
    @Transactional
    public void testDeleteADepartment() {
        Department deptToBeDeleted = departmentService.getDepartmentById(3L);
        assertThat(deptToBeDeleted.getId(), is(3L));
        assertThat(deptToBeDeleted.getDepartmentName(), is("Trade"));

        departmentService.deleteDepartmentById(3L);

        assertThrows(IdNotFoundException.class, () -> {
            Department attemptToFindDeletedDept = departmentService.getDepartmentById(3L);
        });

    }

    @Test
   @Transactional
    public void testDeleteADepartmentWithTickets() {
        Department deptToBeDeleted = departmentService.getDepartmentById(1L);

        // Only one ticket is orphaned at this point
        List<Ticket> orphanedTickets = ticketService.getAllOrphanedTickets();

        assertThat(orphanedTickets.size(), is(1));

        assertThat(deptToBeDeleted.getId(), is(1L));
        assertThat(deptToBeDeleted.getTicketList().size(), is(2));

        departmentService.deleteDepartmentById(1L);

        // Should leave tickets with that department as orphaned
        List<Ticket> allNewOrphanedTickets = ticketService.getAllOrphanedTickets();

        assertThat(allNewOrphanedTickets.size(), is(3));
        assertTrue(allNewOrphanedTickets.contains(deptToBeDeleted.getTicketList().get(0)));
        assertTrue(allNewOrphanedTickets.contains(deptToBeDeleted.getTicketList().get(1)));


        assertThrows(IdNotFoundException.class, () -> {
            Department attemptToFindDeletedDept = departmentService.getDepartmentById(1L);
        });

    }

}
