package org.helpQueue.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "Ticket", schema = "qa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @ToString.Exclude
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "description")
    private String description;

    @Column(name = "time_created", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeCreated;

    @Column(name = "urgency_level")
    private String urgencyLevel;

    @Column(name = "status")
    private String status;

    @Column(name = "last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdated;

    @Column(name = "solution")
    private String solution;


    public Ticket(long id, String title, String author, String description, String urgencyLevel, String status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.urgencyLevel = urgencyLevel;
        this.status = status;
    }

    public Ticket(String title, String author, String description, String urgencyLevel, String status) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.urgencyLevel = urgencyLevel;
        this.status = status;
    }


}
