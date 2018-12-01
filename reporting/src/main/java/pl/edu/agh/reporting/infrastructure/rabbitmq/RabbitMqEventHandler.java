package pl.edu.agh.reporting.infrastructure.rabbitmq;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import pl.edu.agh.reporting.events.ReportingEvent;

@EnableBinding(Sink.class)
public class RabbitMqEventHandler {

    public void handleEvent(ReportingEvent reportingEvent) {

    }

}
