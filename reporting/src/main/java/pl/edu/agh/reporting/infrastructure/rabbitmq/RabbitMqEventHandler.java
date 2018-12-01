package pl.edu.agh.reporting.infrastructure.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import pl.edu.agh.reporting.events.ReportingEvent;

@EnableBinding(Sink.class)
@Slf4j
public class RabbitMqEventHandler {

    @StreamListener(Sink.INPUT)
    public void handleEvent(ReportingEvent reportingEvent) {
        // TODO
        log.info("Received event {}", reportingEvent);
    }

}
