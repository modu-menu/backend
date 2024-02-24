package modu.menu;

import io.swagger.v3.oas.annotations.Hidden;
import modu.menu.core.response.ApiSuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
public class HealthCheckController {

    @GetMapping("/api/health-check")
    public String getHealthCheck() {
        return "up";
    }

}
