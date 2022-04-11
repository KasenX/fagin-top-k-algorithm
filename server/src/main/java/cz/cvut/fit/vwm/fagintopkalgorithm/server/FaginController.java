package cz.cvut.fit.vwm.fagintopkalgorithm.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FaginController {

    private final FaginService service;

    public FaginController(FaginService service) {
        this.service = service;
    }

    @GetMapping("/fagin")
    Result read(@RequestParam int k, @RequestParam String function, @RequestParam int energy, @RequestParam int protein,
                @RequestParam int carbohydrate, @RequestParam int fat) {
        // Validate k
        if (k <= 0)
            throw new IllegalArgumentException("parameter validation failed - the value of parameter k");

        // Validate function
        Function fn = switch (function) {
            case "min" -> Function.MIN;
            case "max" -> Function.MAX;
            case "avg" -> Function.AVG;
            default -> throw new IllegalArgumentException("parameter validation failed - the value of parameter function");
        };

        // Get columns parameters into map
        Map<String, Boolean> columns = new HashMap<>() {{
            put("energy", energy == 1);
            put("protein", protein == 1);
            put("carbohydrate", carbohydrate == 1);
            put("fat", fat == 1);
        }};

        return service.getTopK(k, fn, columns);
    }
}
