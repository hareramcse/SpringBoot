package com.hs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hs.service.LockService;

@RestController
public class LockController {

    @Autowired
    private LockService lockService;

    @GetMapping("/perform/{lockKey}")
    public String performOperation(@PathVariable String lockKey) throws InterruptedException {
        lockService.performWithLock(lockKey);
        return "Operation completed";
    }
}

