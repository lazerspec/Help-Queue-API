package org.helpQueue.persistence;

import org.helpQueue.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {

    @Query(value = "SELECT * FROM qa.TICKET WHERE department_id IS NULL", nativeQuery = true)
    List<Ticket> findOrphanedTickets();



}
