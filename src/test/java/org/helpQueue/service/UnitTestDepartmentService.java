package org.helpQueue.service;

import lombok.extern.slf4j.Slf4j;
import org.helpQueue.domain.Department;
import org.helpQueue.domain.Ticket;
import org.helpQueue.exception.IdNotFoundException;
import org.helpQueue.persistence.DepartmentRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ActiveProfiles(profiles = "test")
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UnitTestDepartmentService {

    @Autowired
    private DepartmentService departmentService;

    @MockBean
    private DepartmentRepository departmentRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    List<Department> testDepartmentList = this.getTestDepartmentsList();

    @Before
    public void setup() {
        Department department = new Department();
        department.setDepartmentName("Engineering");

        when(departmentRepository.findAll()).thenReturn(testDepartmentList);
        when(departmentRepository.save(department)).thenReturn(department);
    }

    @Test
    public void testRetrieveAllDepartments() {
        List<Department> departmentList = departmentService.getAllDepartments();
        System.out.println(departmentList);

        assertThat(departmentList, is(testDepartmentList));
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    public void testGetDepartmentById() throws Exception {
        Department department = new Department();
        department.setId(2);
        department.setDepartmentName("Fake Department");
        when(departmentRepository.findById(2L)).thenReturn(Optional.of(department));

        Department departmentFromRepository = departmentService.getDepartmentById(2L);

        assertThat(departmentFromRepository, is(department));
        verify(departmentRepository, times(1)).findById(2L);

    }

    @Test
    public void testGetInvalidId() {
        expectedException.expect(IdNotFoundException.class);
        Department department = departmentService.getDepartmentById(3L);
    }

    @Test
    public void testCreateADepartmentWithNoTickets() {
        Department department = new Department();
        department.setDepartmentName("Engineering");

        Department savedDepartment = departmentService.saveADepartment(department);
        assertThat(savedDepartment, is(department));

        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    public void testCreateADepartmentWithTickets() {
        when(departmentRepository.save(this.getDepartmentWithTickets())).thenReturn(this.getDepartmentWithTickets());

        Department department = departmentService.updateADepartment(this.getDepartmentWithTickets());

        assertThat(department, is(this.getDepartmentWithTickets()));
        assertThat(department.getTicketList(), is(this.getDepartmentWithTickets().getTicketList()));
        verify(departmentRepository, times(1)).save(this.getDepartmentWithTickets());
    }

    @Test
    public void testUpdatingADepartment() {
        Department existingDept = new Department();
        existingDept.setId(1L);
        existingDept.setDepartmentName("Dept1");

        Department updatedDept = new Department();
        updatedDept.setId(1L);
        updatedDept.setDepartmentName("UpdatedDepartment");

        when(departmentRepository.save(existingDept)).thenReturn(updatedDept);

        Department returnedDepartment = departmentService.updateADepartment(existingDept);

        assertThat(returnedDepartment, is(updatedDept));
        verify(departmentRepository, times(1)).save(existingDept);
    }

    @Test
    public void testDeleteADepartment() {
        Department toBeDeleted = this.getDepartmentWithTickets();

        for (Ticket ticket : toBeDeleted.getTicketList()) {
            ticket.setDepartment(toBeDeleted);
        }

        when(departmentRepository.findById(toBeDeleted.getId())).thenReturn(Optional.of(toBeDeleted));

        doNothing().when(departmentRepository).deleteById(toBeDeleted.getId());

        departmentService.deleteDepartmentById(toBeDeleted.getId());

        assertNull(toBeDeleted.getTicketList().get(0).getDepartment());
        verify(departmentRepository,times(1)).deleteById(toBeDeleted.getId());
        verify(departmentRepository,times(1)).findById(toBeDeleted.getId());
    }


// todo - finish off testing with int testing of department and ticket services and controllers

    // Service this is unit test, need to do int test
    // ticket need to do unit test, then int test
    // And custom SQL

    // Same with controllers





    private List<Department> getTestDepartmentsList() {
        List<Department> departmentList = new ArrayList<>();

        Department department = new Department();
        department.setId(1L);
        department.setDepartmentName("Dept1");
        department.setTicketList(this.getATicketList());

        for (Ticket ticket : department.getTicketList()) {
            ticket.setDepartment(department);
        }

        Department departmentWithNoTicket = new Department();
        departmentWithNoTicket.setId(2L);
        departmentWithNoTicket.setDepartmentName("Dept2");

        departmentList.add(department);
        departmentList.add(departmentWithNoTicket);

        return departmentList;
    }


    private Department getDepartmentWithTickets() {
        Department department = new Department();
        department.setId(1L);
        department.setDepartmentName("Trade");

        department.setTicketList(this.getATicketList());

        return department;
    }

    private List<Ticket> getATicketList() {
        List<Ticket> ticketList = new ArrayList<>();

        Ticket ticket = new Ticket();
        ticket.setId(1);
        ticket.setTitle("Ticket1");
        ticket.setAuthor("Person1");
        ticket.setDescription("A fake description");
        ticket.setUrgencyLevel("Major");
        ticket.setStatus("OPEN");

        ticketList.add(ticket);

        return ticketList;
    }



}
