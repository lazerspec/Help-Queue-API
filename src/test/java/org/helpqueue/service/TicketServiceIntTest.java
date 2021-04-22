package org.helpqueue.service;

import lombok.extern.slf4j.Slf4j;
import org.helpqueue.domain.Department;
import org.helpqueue.domain.Ticket;
import org.helpqueue.exception.IdNotFoundException;
import org.helpqueue.persistence.TicketRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@Slf4j
@ActiveProfiles(profiles = "test")
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(scripts = "classpath:data-test.sql")
public class TicketServiceIntTest {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private TicketRepository ticketRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {

    }

    @Test
    public void testGetTicketsFromDatabase() {
        List<Ticket> ticketList = ticketService.getAllTickets();

        assertThat(ticketList.size(),is(5));
        assertThat(ticketList.get(0).getTitle(), is("Ticket1"));
    }

    @Test
    public void testGetOrphanTickets() {
        List<Ticket> ticketList = ticketService.getAllOrphanedTickets();

        assertThat(ticketList.size(), is(1));
        assertThat(ticketList.get(0).getTitle(), is("Ticket3"));
        assertThat(ticketList.get(0).getDescription(), is("An orphaned ticket description"));
    }

    @Test
    public void testGetTicketById() {
        Ticket ticket = ticketService.findTicketById(2L);

        assertThat(ticket.getDepartment().getDepartmentName(), is("Wealth"));
        assertThat(ticket.getId(), is(2L));
        assertThat(ticket.getStatus(), is("open"));
    }

    @Test
    public void testGetTicketWithInvalidId() {
        expectedException.expect(IdNotFoundException.class);
        Ticket ticket = ticketService.findTicketById(90L);
    }

    @Test
    public void testCreateAnOrphanTicket() {
        Ticket ticketToBeCreated = new Ticket("Title1","Author1","Description1","high","open");
        Ticket ticketJustCreated = ticketService.saveATicket(ticketToBeCreated);

        assertThat(ticketJustCreated.getId(), is(6L));
        assertThat(ticketJustCreated.getTitle(), is("Title1"));

        List<Ticket> orphanTickets = ticketService.getAllOrphanedTickets();
        assertTrue(orphanTickets.contains(ticketJustCreated));
    }

    @Test
    @Transactional
    public void testCreateATicketWithADepartment() {
        Ticket ticketToBeCreated = new Ticket("Title1","Author1","Description1","high","open");
        Department department = departmentService.getDepartmentById(3L);

        ticketToBeCreated.setDepartment(department);

        Ticket ticketJustCreated = ticketService.saveATicket(ticketToBeCreated);

        assertThat(ticketJustCreated.getDepartment(), is(department));
        assertThat(ticketJustCreated.getId(), is(6L));
        assertThat(ticketJustCreated.getTitle(), is("Title1"));
    }

    @Test
    public void testUpdateATicket() {
        Ticket ticketToBeUpdated = ticketService.findTicketById(1L);

        assertThat(ticketToBeUpdated.getUrgencyLevel(), is("high"));

        ticketToBeUpdated.setUrgencyLevel("low");
        Ticket updatedTicket = ticketService.updateATicket(ticketToBeUpdated);

        assertThat(updatedTicket.getUrgencyLevel(), is("low"));
        assertThat(updatedTicket.getId(), is(1L));
    }

    @Test
    public void testUpdateAnOrphanTicketToADepartment() {
        Ticket orphanTicket = ticketService.findTicketById(4L);
        assertNull(orphanTicket.getDepartment());

        Department department = departmentService.getDepartmentById(3L);
        orphanTicket.setDepartment(department);
        Ticket ticket = ticketService.updateATicket(orphanTicket);

        assertThat(ticket.getDepartment(), is(department));
    }

    @Test
    @Transactional
    public void testAssignATicketToANewDepartment() {
        Ticket ticket = ticketService.findTicketById(1L);

        assertThat(ticket.getDepartment().getDepartmentName(), is("Wealth"));

        Department department = departmentService.getDepartmentById(2L);
        ticket.setDepartment(department);

        Ticket updatedTicket = ticketService.updateATicket(ticket);
        assertThat(updatedTicket.getDepartment().getDepartmentName(), is("CoreEng"));
    }

    @Test
    @Transactional
    public void testDeleteATicket() {
        expectedException.expect(IdNotFoundException.class);

        Ticket ticket = ticketService.findTicketById(2L);
        assertThat(ticket.getId(), is(2L));
        assertThat(ticket.getUrgencyLevel(), is("high"));

        ticketService.deleteATicket(2L);

        // Attempt to delete again
        ticketService.deleteATicket(2L);
    }

    @Test
    public void testDeleteATicketNotPresent() {
        expectedException.expect(IdNotFoundException.class);
        ticketService.deleteATicket(50L);
    }




}
