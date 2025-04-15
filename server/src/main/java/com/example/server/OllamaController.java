package com.example.server;


import com.example.server.OllamaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ollama")
public class OllamaController {
    private final OllamaService ollamaService;

    public OllamaController(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generate(@RequestBody GenerateRequest request) {
        if (request.model() == null || request.model().isBlank()) {
            return ResponseEntity.badRequest().body("Model cannot be empty");
        }
        if (request.prompt() == null || request.prompt().isBlank()) {
            return ResponseEntity.badRequest().body("Prompt cannot be empty");
        }

        try {
            String response = ollamaService.generate(request.model(), request.prompt());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error processing request: " + e.getMessage());
        }
    }

    public record GenerateRequest(String model, String prompt) {}
}