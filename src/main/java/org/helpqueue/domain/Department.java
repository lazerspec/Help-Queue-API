package org.helpqueue.domain;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Department", schema = "qa")
@Data
@NoArgsConstructor
public class Department {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "department_name")
    private String departmentName;

    @JsonManagedReference
    @ToString.Exclude
    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Ticket> ticketList = new ArrayList<>();

    public Department(long id, String departmentName) {
        this.id = id;
        this.departmentName = departmentName;
    }

    public Department(String departmentName) {
        this.departmentName = departmentName;
    }

}
