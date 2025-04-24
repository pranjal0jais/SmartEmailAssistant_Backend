package com.pranjal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/api/generate")
@CrossOrigin(origins = "http://localhost:3000/")
public class Controller {

    private EmailService emailService;

    public Controller(EmailService emailService){
        this.emailService = emailService;
    }

    @PostMapping("/reply")
    public ResponseEntity<String> getReply(@RequestBody Map<String, String> request){
        String email = request.get("email");
        String tone = request.get("tone");
        return ResponseEntity.ok(emailService.generateResponse(email, tone));
    }

    @PostMapping("/write")
    public ResponseEntity<String> getEmail(@RequestBody Map<String, String> request){
        String description = request.get("description");
        String tone = request.get("tone");
        return ResponseEntity.ok(emailService.generateEmail(description, tone));
    }

    @PostMapping("/summary")
    public ResponseEntity<String> getSummary(@RequestBody Map<String, String> request){
        String email = request.get("email");
        return ResponseEntity.ok(emailService.generateSummary(email));
    }
}
