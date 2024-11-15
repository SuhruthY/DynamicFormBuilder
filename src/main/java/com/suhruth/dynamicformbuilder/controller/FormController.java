package com.suhruth.dynamicformbuilder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suhruth.dynamicformbuilder.service.FormGeneratorService;

@RestController
@RequestMapping("/api/forms")
public class FormController {

    @Autowired
    private FormGeneratorService formGeneratorService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateForm(@RequestBody String formJson) {
        try {
            String htmlForm = formGeneratorService.generateHtmlForm(formJson);
            return ResponseEntity.ok(htmlForm);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid JSON format: " + e.getMessage());
        }
    }
}
