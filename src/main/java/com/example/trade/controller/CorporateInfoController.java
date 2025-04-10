package com.example.trade.controller;

import com.example.trade.dto.BusinessInputDto;
import com.example.trade.exception.InvalidDistrictException;
import com.example.trade.service.BusinessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CorporateInfoController {
    private final BusinessService businessService;

    @PostMapping("/corporate")
    public ResponseEntity<?> saveCorporateData(@RequestBody @Valid BusinessInputDto input){
        try {
            businessService.process(input);
        }catch(InvalidDistrictException | IOException e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}