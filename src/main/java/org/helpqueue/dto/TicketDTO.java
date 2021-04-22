package org.helpqueue.dto;

import lombok.Data;
import org.helpqueue.domain.Department;

import java.util.Date;

@Data
public class TicketDTO {

    private long id;
    private Department department;
    private String title;
    private String author;
    private String description;
    private Date timeCreated;
    private String urgencyLevel;
    private String status;
    private Date lastUpdated;
    private String solution;



}
