package org.lazerspec.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/api/v1")
public class CRUDController {

    @GetMapping("/starter")
    public String springTest() {
        log.info("We've hit the starter endpoint!");
        return "Spring boot skeleton works!";
    }

}
