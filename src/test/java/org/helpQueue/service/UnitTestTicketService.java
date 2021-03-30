package org.helpQueue.service;


import lombok.extern.slf4j.Slf4j;
import org.helpQueue.domain.Ticket;
import org.helpQueue.exception.IdNotFoundException;
import org.helpQueue.persistence.TicketRepository;
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
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ActiveProfiles(profiles = "test")
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UnitTestTicketService {

    @Autowired
    private TicketService ticketService;

    @MockBean
    private TicketRepository ticketRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        when(ticketRepository.findAll()).thenReturn(this.getATicketList());
        when(ticketRepository.findOrphanedTickets()).thenReturn(this.getATicketList());
    }

    @Test
    public void testGetAllTickets() {
        List<Ticket> ticketList = ticketRepository.findAll();

        assertThat(ticketList.size(), is(this.getATicketList().size()));
        verify(ticketRepository, times(1)).findAll();
    }

    @Test
    public void testGetOrphanedTickets() {
        List<Ticket> ticketList = ticketRepository.findOrphanedTickets();

        assertThat(ticketList.size(), is(this.getATicketList().size()));
        verify(ticketRepository, times(1)).findOrphanedTickets();
    }

    @Test
    public void testGetTicketById() {
        Ticket ticket = new Ticket(1,null,"Title","Author","A Desc",new Date(),"URGENT","OPEN", new Date());
        when(ticketRepository.findById(1L)).thenReturn(java.util.Optional.of(ticket));

        Ticket ticketFromRepository = ticketService.findTicketById(1L);

        assertThat(ticketFromRepository, is(ticket));
        verify(ticketRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetTicketWithInvalidId() {
        expectedException.expect(IdNotFoundException.class);
        Ticket ticket = ticketService.findTicketById(4L);
    }

    @Test
    public void testCreateATicket() {
        Ticket ticketToSave = new Ticket("TicketToSave","Author","A Desc","URGENT","OPEN");
        Ticket ticketToReturnFromRepository = new Ticket(1,null,"TicketToSave","Author","A Desc",new Date(),"URGENT","OPEN", new Date());

        when(ticketRepository.save(ticketToSave)).thenReturn(ticketToReturnFromRepository);

        Ticket ticket = ticketService.saveATicket(ticketToSave);

        assertThat(ticket.getAuthor(), is(ticketToReturnFromRepository.getAuthor()));
        assertThat(ticket.getUrgencyLevel(), is(ticketToReturnFromRepository.getUrgencyLevel()));
        assertThat(ticketToReturnFromRepository.getId(), is(1L));
        verify(ticketRepository, times(1)).save(ticketToSave);
    }

    @Test
    public void testUpdateATicket() {
        Ticket ticketToBeUpdated = this.getASingleTicket();

        Ticket updatedTicket = ticketToBeUpdated;
        updatedTicket.setUrgencyLevel("MINOR");

        when(ticketRepository.save(ticketToBeUpdated)).thenReturn(updatedTicket);
        Ticket ticketFromUpdate = ticketService.updateATicket(ticketToBeUpdated);

        assertThat(ticketFromUpdate, is(updatedTicket));
        verify(ticketRepository, times(1)).save(ticketToBeUpdated);
    }

    @Test
    public void testDeleteATicketI() {
        Ticket ticketToBeDeleted = this.getASingleTicket();
        doNothing().when(ticketRepository).deleteById(ticketToBeDeleted.getId());
        ticketService.deleteATicket(ticketToBeDeleted.getId());

        verify(ticketRepository, times(1)).deleteById(ticketToBeDeleted.getId());
    }



    private List<Ticket> getATicketList() {
        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(new Ticket(1,null,"Title","Author","A Desc",new Date(),"URGENT","OPEN", new Date()));
        ticketList.add(new Ticket(2,null,"Title2","Author2","A Desc2",new Date(),"URGENT","OPEN", new Date()));

        return ticketList;
    }

    private Ticket getASingleTicket() {
        return new Ticket(1,null,"Title","Author","A Desc",new Date(),"URGENT","OPEN", new Date());
    }



}
