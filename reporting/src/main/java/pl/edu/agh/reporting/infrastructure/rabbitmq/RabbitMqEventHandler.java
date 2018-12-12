package pl.edu.agh.reporting.infrastructure.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.function.json.JacksonMapper;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import pl.edu.agh.reporting.ReportingEventType;
import pl.edu.agh.reporting.application.ReportingApplicationService;
import pl.edu.agh.reporting.events.ReportingEvent;

@EnableBinding(Sink.class)
@Slf4j
public class RabbitMqEventHandler {

    private final ReportingApplicationService reportingApplicationService;

    private final JacksonMapper jacksonMapper;

    public RabbitMqEventHandler(ReportingApplicationService reportingApplicationService, JacksonMapper jacksonMapper) {
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
        final String textEventType = headers.get("type", String.class);
        return ReportingEventType.valueOf(textEventType);
    }

    private ReportingEvent deserializeEvent(Message<String> message, ReportingEventType eventType) {
        return jacksonMapper.toObject(message.getPayload(), eventType.getEventClass());
    }

}
