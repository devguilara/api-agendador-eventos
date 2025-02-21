package com.devguilara.api.cadastroeventos.controller;


import com.devguilara.api.cadastroeventos.DTO.ErrorMessage;
import com.devguilara.api.cadastroeventos.DTO.SubscriptionConflictException;
import com.devguilara.api.cadastroeventos.DTO.SubscriptionRes;
import com.devguilara.api.cadastroeventos.DTO.UserIndicadorNotFoundException;
import com.devguilara.api.cadastroeventos.exceptions.EventNotFoundException;
import com.devguilara.api.cadastroeventos.model.User;
import com.devguilara.api.cadastroeventos.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping({"/subscription/{prettyName}", "/subscription/{prettyName}/{userId}"})
    public ResponseEntity<?> createSubscription(
            @PathVariable String prettyName,
            @RequestBody User subscriber,
            @PathVariable(required = false) Integer userId)
    {
        try{
            SubscriptionRes subscription = subscriptionService.createNewSubscription(prettyName, subscriber, userId);
            if (subscription != null) {
                return ResponseEntity.ok(subscription);
            }
        }catch (EventNotFoundException e){
            return ResponseEntity.status(404).body(new ErrorMessage(e.getMessage()));
        }catch (SubscriptionConflictException e){
            return ResponseEntity.status(409).body(new ErrorMessage(e.getMessage()));
        }catch(UserIndicadorNotFoundException e){
            return ResponseEntity.status(404).body(new ErrorMessage(e.getMessage()));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/subscription/{prettyName}/ranking")
    public ResponseEntity<?> generateRankingByEvent(
            @PathVariable String prettyName
    ){
        try {
            return  ResponseEntity.ok(subscriptionService.getCompleteRanking(prettyName).subList(0,3));
        }catch (EventNotFoundException e){
            return ResponseEntity.status(404).body(new ErrorMessage(e.getMessage()));
        }
    }

    @GetMapping("/subscription/{prettyName}/ranking/{userId}")
    public ResponseEntity<?> getRankingByUser(
            @PathVariable String prettyName,
            @PathVariable Integer userId
    ){
       try{
           return ResponseEntity.ok(subscriptionService.getRankingByUser(prettyName,userId));
       }catch (Exception e){
           return ResponseEntity.status(404).body(new ErrorMessage(e.getMessage()));
       }
    }
}
