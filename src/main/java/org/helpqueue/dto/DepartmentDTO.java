package org.helpqueue.dto;


import lombok.Data;
import org.helpqueue.domain.Ticket;

import java.util.List;

@Data
public class DepartmentDTO {

    private long id;
    private String departmentName;
    private List<Ticket> ticketList;

}
