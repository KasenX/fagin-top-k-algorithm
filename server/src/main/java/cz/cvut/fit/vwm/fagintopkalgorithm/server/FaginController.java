package cz.cvut.fit.vwm.fagintopkalgorithm.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
public class FaginController {

    private final FaginService service;

    public FaginController(FaginService service) {
        this.service = service;
    }

    @GetMapping("/{query}")
    Result read(@PathVariable String query) {
        // Parse query
        Map<String, String> parameters =
                UriComponentsBuilder.fromUriString(query).build().getQueryParams().toSingleValueMap();

        // Validate parameters
        Set<String> keys = parameters.keySet();
        if (keys.size() != 6 || !keys.contains("k") || !(keys.contains("function")) || !keys.contains("energy")
                || !keys.contains("protein") || !keys.contains("carbohydrate") || !keys.contains("fat"))
            throw new IllegalArgumentException("parameter validation failed - the presence of all parameters");

        int k = -1;
        String fn = "";
        Map<String, Boolean> columns = new HashMap<>();
        // Parse parameters
        for (var parameter : keys) {
            // Parameter k
            if (parameter.equals("k"))
                k = Integer.parseInt(parameters.get(parameter));
            // Function parameter
            else if (parameter.equals("function"))
                fn = parameters.get(parameter);
            // Columns parameters
            else
                columns.put(parameter, parameters.get(parameter).equals("1"));
        }
        // Validate k
        if (k <= 0)
            throw new IllegalArgumentException("parameter validation failed - the value of parameter k");

        // Validate function
        Function function = switch (fn) {
            case "min" -> Function.MIN;
            case "max" -> Function.MAX;
            case "avg" -> Function.AVG;
            default -> throw new IllegalArgumentException("parameter validation failed - the value of parameter function");
        };

        return service.getTopK(k, function, columns);
    }
}
