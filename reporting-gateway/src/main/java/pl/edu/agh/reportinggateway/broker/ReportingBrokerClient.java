package pl.edu.agh.reportinggateway.broker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import pl.edu.agh.reporting.ReportingEventType;
import pl.edu.agh.reporting.events.ReportingEvent;

@EnableBinding(Source.class)
public class ReportingBrokerClient {

    private final Source source;

    @Autowired
    public ReportingBrokerClient(Source source) {
        this.source = source;
    }

    public void sendEvent(ReportingEvent event, ReportingEventType type) {
        final ReportingEventHeaders messageHeaders = new ReportingEventHeaders(type);
        final Message<ReportingEvent> message = MessageBuilder.createMessage(event, messageHeaders);
        source.output().send(message);
    }

}
