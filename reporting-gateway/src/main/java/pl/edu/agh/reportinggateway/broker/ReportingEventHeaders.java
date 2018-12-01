package pl.edu.agh.reportinggateway.broker;

import org.springframework.messaging.MessageHeaders;
import pl.edu.agh.reporting.ReportingEventType;

import java.util.HashMap;
import java.util.Map;

public class ReportingEventHeaders extends MessageHeaders {

    public ReportingEventHeaders(ReportingEventType type) {
        super(headersWithType(type));
    }

    private static Map<String, Object> headersWithType(ReportingEventType type) {
        return new HashMap<String, Object>() {{
            put("type", type);
        }};
    }

}
