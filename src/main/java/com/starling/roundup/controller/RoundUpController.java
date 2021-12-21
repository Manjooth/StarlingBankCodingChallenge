package com.starling.roundup.controller;

import com.starling.roundup.components.SavingGoal;
import com.starling.roundup.services.RoundUpService;
import com.starling.roundup.services.SavingGoalService;
import com.starling.roundup.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/starling")
public class RoundUpController
{

    @Autowired
    private RoundUpService roundUpService;

    @GetMapping(value = "/round-up")
    public ResponseEntity roundUp()
    {
        final List<SavingGoal> response = roundUpService.roundUp();
        return ResponseEntity.ok(response);
    }

}
