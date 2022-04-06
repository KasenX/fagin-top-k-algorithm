package cz.cvut.fit.vwm.fagintopkalgorithm.server;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
public class FoodController {

    private final FoodService service;

    public FoodController(FoodService service) {
        this.service = service;
    }

    @GetMapping("/{query}")
    Result read(@PathVariable String query) {
        // Parse query
        Map<String, String> parameters =
                UriComponentsBuilder.fromUriString(query).build().getQueryParams().toSingleValueMap();

        // Validate parameters
        Set<String> keys = parameters.keySet();
        if (keys.size() != 6 || !keys.contains("k") || !keys.contains("energy") || !keys.contains("protein")
                || !keys.contains("carbohydrate") || !keys.contains("fat") || !keys.contains("fiber"))
            throw new IllegalArgumentException("parameter validation failed");

        int k = -1;
        Map<String, Boolean> columns = new HashMap<>();
        // Parse parameters
        for (var parameter : keys) {
            // Parameter k
            if (parameter.equals("k"))
                k = Integer.parseInt(parameters.get(parameter));
            // Columns parameters
            else
                columns.put(parameter, parameters.get(parameter).equals("true"));
        }
        // Validate k values
        if (k <= 0)
            throw new IllegalArgumentException("k parameter must be >= 0");

        return service.getTopK(k, columns);
    }
}
