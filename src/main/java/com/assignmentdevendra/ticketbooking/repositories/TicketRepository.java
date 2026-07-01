package com.assignmentdevendra.ticketbooking.repositories;

import com.assignmentdevendra.ticketbooking.model.Ticket;
import com.assignmentdevendra.ticketbooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByUser(User user);
}