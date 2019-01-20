package pl.edu.agh.reporting.reports.infrastructure.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.function.json.JacksonMapper;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.kafka.support.DefaultKafkaHeaderMapper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import pl.edu.agh.reporting.ReportingEventType;
import pl.edu.agh.reporting.events.ReportingEvent;
import pl.edu.agh.reporting.reports.application.ReportingApplicationService;

@EnableBinding(Sink.class)
@Slf4j
public class KafkaEventHandler {

    private final ReportingApplicationService reportingApplicationService;

    private final JacksonMapper jacksonMapper;

    public KafkaEventHandler(ReportingApplicationService reportingApplicationService, JacksonMapper jacksonMapper) {
        this.reportingApplicationService = reportingApplicationService;
        this.jacksonMapper = jacksonMapper;
    }

    @StreamListener(Sink.INPUT)
    public void handleEvent(Message<String> message) {
        log.info("Received event {}", message);

        final MessageHeaders headers = message.getHeaders();

        final ReportingEventType eventType = resolveEventType(headers);
        final ReportingEvent reportingEvent = deserializeEvent(message, eventType);

        reportingApplicationService.handle(reportingEvent, eventType);
    }

    private ReportingEventType resolveEventType(MessageHeaders headers) {
        byte[] typeHeaderValue = headers.get("type", DefaultKafkaHeaderMapper.NonTrustedHeaderType.class).getHeaderValue();
        final String textEventType = new String(typeHeaderValue, 1, typeHeaderValue.length -2);
        return ReportingEventType.valueOf(textEventType);
    }

    private ReportingEvent deserializeEvent(Message<String> message, ReportingEventType eventType) {
        return jacksonMapper.toObject(message.getPayload(), eventType.getEventClass());
    }

}
