package modu.menu;

import modu.menu.core.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/api/health-check")
    public String getHealthCheck() {
        return "up";
    }

    @GetMapping("/api/test")
    public ResponseEntity<?> getResponseTest() {
        return ResponseEntity.ok().body(new ApiResponse<>("test"));
    }
}
