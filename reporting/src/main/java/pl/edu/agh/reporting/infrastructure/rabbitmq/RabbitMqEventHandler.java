package pl.edu.agh.reporting.infrastructure.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import pl.edu.agh.reporting.ReportingEventType;
import pl.edu.agh.reporting.application.ReportingApplicationService;
import pl.edu.agh.reporting.events.BetSlipPlaced;
import pl.edu.agh.reporting.events.ReportingEvent;

@EnableBinding(Sink.class)
@Slf4j
public class RabbitMqEventHandler {

    private final ReportingApplicationService reportingApplicationService;

    public RabbitMqEventHandler(ReportingApplicationService reportingApplicationService) {
        this.reportingApplicationService = reportingApplicationService;
    }

    @StreamListener(Sink.INPUT)
    public void handleEvent(Message<BetSlipPlaced> message) {
        dispatchEvent(message);
    }

    private void dispatchEvent(Message<? extends ReportingEvent> message) {
        log.info("Received event {}", message);

        final MessageHeaders headers = message.getHeaders();
        final ReportingEvent reportingEvent = message.getPayload();

        final String textEventType = headers.get("type", String.class);
        final ReportingEventType eventType = ReportingEventType.valueOf(textEventType);

        reportingApplicationService.handle(reportingEvent, eventType);
    }

}
