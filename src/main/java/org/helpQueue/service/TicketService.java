package org.helpQueue.service;

import lombok.extern.slf4j.Slf4j;
import org.helpQueue.domain.Ticket;
import org.helpQueue.exception.IdNotFoundException;
import org.helpQueue.persistence.TicketRepository;
import org.springframework.dao.EmptyResultDataAccessException;
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
        try {
            ticketRepository.deleteById(ticketId);
        } catch (EmptyResultDataAccessException e) {
            log.error("Error", e);
            throw new IdNotFoundException(ticketId);
        }
    }


}
