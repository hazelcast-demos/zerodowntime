package org.hazelcast.zerodowntime.operation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OperationController {

    private final OperationContext context;

    public OperationController(OperationContext context) {
        this.context = context;
    }

    @GetMapping("/rest/ops")
    public OperationView getOperation() {
        return new OperationView(
                context.getHostname(),
                context.getVersion()
        );
    }
}