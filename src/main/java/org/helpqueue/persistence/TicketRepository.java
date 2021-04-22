package org.helpqueue.persistence;

import org.helpqueue.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {

    @Query(value = "SELECT * FROM qa.TICKET WHERE department_id IS NULL", nativeQuery = true)
    List<Ticket> findOrphanedTickets();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM qa.TICKET WHERE id = ?1", nativeQuery = true)
    int deleteATicket(@Param("id") long id);

}
