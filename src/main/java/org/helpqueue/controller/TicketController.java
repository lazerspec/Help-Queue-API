package org.helpqueue.controller;

import lombok.extern.slf4j.Slf4j;
import org.helpqueue.domain.Ticket;
import org.helpqueue.dto.TicketDTO;
import org.helpqueue.service.TicketService;
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
@RequestMapping(value = "/api/v1/ticket")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }


    @GetMapping("/find/all")
    public ResponseEntity<List<Ticket>> getTickets() {
        log.info("Obtaining all tickets");
        return new ResponseEntity<>(ticketService.getAllTickets(), HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable(name = "id") long id) {
        log.info("Finding ticket with id: {}", id);
        return new ResponseEntity<>(ticketService.findTicketById(id), HttpStatus.OK);
    }

    @GetMapping("/find/orphaned")
    public ResponseEntity<List<Ticket>> getOrphanedTickets() {
        log.info("Obtaining all tickets without a department");
        return new ResponseEntity<>(ticketService.getAllOrphanedTickets(), HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Ticket> saveATicket(@RequestBody TicketDTO ticketDTO) {
        Ticket persistentTicket = new Ticket();
        persistentTicket.setDepartment(ticketDTO.getDepartment());
        persistentTicket.setUrgencyLevel(ticketDTO.getUrgencyLevel());
        persistentTicket.setStatus(ticketDTO.getStatus());
        persistentTicket.setDescription(ticketDTO.getDescription());
        persistentTicket.setTitle(ticketDTO.getTitle());
        persistentTicket.setAuthor(ticketDTO.getAuthor());
        persistentTicket.setSolution(ticketDTO.getSolution());


        log.info("Saving ticket: {}", persistentTicket);
        return new ResponseEntity<>(ticketService.saveATicket(persistentTicket), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Ticket> updateATicket(@RequestBody TicketDTO ticketDTO) {

        Ticket persistentTicket = new Ticket();
        persistentTicket.setDepartment(ticketDTO.getDepartment());
        persistentTicket.setUrgencyLevel(ticketDTO.getUrgencyLevel());
        persistentTicket.setStatus(ticketDTO.getStatus());
        persistentTicket.setDescription(ticketDTO.getDescription());
        persistentTicket.setTitle(ticketDTO.getTitle());
        persistentTicket.setAuthor(ticketDTO.getAuthor());
        persistentTicket.setSolution(ticketDTO.getSolution());

        persistentTicket.setLastUpdated(ticketDTO.getLastUpdated());
        persistentTicket.setTimeCreated(ticketDTO.getTimeCreated());
        persistentTicket.setId(ticketDTO.getId());

        log.info("Updating ticket: {}", persistentTicket);
        return new ResponseEntity<>(ticketService.updateATicket(persistentTicket), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteATicket(@PathVariable(name = "id") long id) {
        log.info("Deleting ticket with Id: {}", id);
        ticketService.deleteATicket(id);
        return ResponseEntity.noContent().build();
    }


}
