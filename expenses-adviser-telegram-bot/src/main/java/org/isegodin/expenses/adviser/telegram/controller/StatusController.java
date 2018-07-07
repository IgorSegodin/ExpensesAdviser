package org.isegodin.expenses.adviser.telegram.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author isegodin
 */
@RestController
public class StatusController {

    @GetMapping(path = "/status")
    public String status() {
        return "OK";
    }

}
