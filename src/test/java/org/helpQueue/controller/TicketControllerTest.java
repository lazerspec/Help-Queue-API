package org.helpQueue.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.helpQueue.domain.Ticket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:data-test.sql")
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TicketControllerTest {

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testSaveTicket() throws Exception {
        Ticket ticket = new Ticket("title","person1","A ticket","medium","open");
        Ticket savedTicket = new Ticket(6L,null,"title","person1","A ticket",new Date(),"medium","open", new Date(),null);

        this.mvc.perform(post("/api/v1/ticket/save")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(this.objectMapper.writeValueAsString(ticket)))
                .andExpect(status().isCreated())
                .andExpect(content().json(this.objectMapper.writeValueAsString(savedTicket)));
    }

    @Test
    public void testGetAllTickets() throws Exception {
        this.mvc.perform(get("/api/v1/ticket/find/all")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(5)));
    }

    @Test
    public void testGetTicketById() throws Exception {
        this.mvc.perform(get("/api/v1/ticket/find/{id}",1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Ticket1"))
                .andExpect(jsonPath("$.status").value("open"))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testUpdateTicket() throws Exception {
        Ticket ticket = new Ticket(2L,"title","person1","A ticket which has been updated","medium","in progress");

        this.mvc.perform(put("/api/v1/ticket/update")
                .content(this.objectMapper.writeValueAsString(ticket))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.description").value("A ticket which has been updated"))
                .andExpect(jsonPath("$.status").value("in progress"));
    }

    @Test
    public void testGetAllOrphanedTickets() throws Exception {
        this.mvc.perform(get("/api/v1/ticket/find/orphaned")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)));
    }

    @Test
    public void testDeleteTicket() throws Exception {
        this.mvc.perform(delete("/api/v1/ticket/delete/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
