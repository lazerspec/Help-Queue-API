package org.helpqueue.service;

import lombok.extern.slf4j.Slf4j;
import org.helpqueue.domain.Ticket;
import org.helpqueue.exception.IdNotFoundException;
import org.helpqueue.persistence.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public List<Ticket> getAllOrphanedTickets() {
        return ticketRepository.findOrphanedTickets();
    }

    public Ticket saveATicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Ticket updateATicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Ticket findTicketById(long id) {
        return ticketRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
    }

    public void deleteATicket(long ticketId) {
        int rowsAffected = ticketRepository.deleteATicket(ticketId);

        if (rowsAffected != 1) {
            throw new IdNotFoundException(ticketId);
        }
    }


}
