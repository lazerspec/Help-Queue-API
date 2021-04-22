package org.helpqueue.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.helpqueue.domain.Department;
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
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void testGetAllDepartments() throws Exception {
        this.mvc.perform(get("/api/v1/department/find/all")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(3)));
    }

    @Test
    public void testGetDepartmentById() throws Exception {
        this.mvc.perform(get("/api/v1/department/find/{id}",1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.departmentName").value("Wealth"))
                .andExpect(jsonPath("$.ticketList.*", hasSize(2)))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testSaveDepartment() throws Exception {
        Department department = new Department("Finance");
        Department savedDepartment = new Department(4L,"Finance");

        this.mvc.perform(post("/api/v1/department/save")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(department)))
                .andExpect(status().isCreated())
                .andExpect(content().json(this.objectMapper.writeValueAsString(savedDepartment)));
    }

    @Test
    public void testUpdateDepartment() throws Exception {

        Department updatedDepartment = new Department(2L,"Wealth Operations");
        this.mvc.perform(put("/api/v1/department/update")
                .content(this.objectMapper.writeValueAsString(updatedDepartment))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.departmentName").value("Wealth Operations"));

    }

    @Test
    public void testDeleteDepartment() throws Exception {
        this.mvc.perform(delete("/api/v1/department/delete/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
