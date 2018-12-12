package pl.edu.agh.reporting.domain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import pl.edu.agh.reporting.events.ReportingEvent;

import java.util.List;

public abstract class Aggregate {

    private final List<ReportingEvent> nonCommitedEvents = Lists.newArrayList();

    public abstract void apply(ReportingEvent event);

    protected void appendEvent(ReportingEvent event) {
        this.nonCommitedEvents.add(event);
    }

    public List<? extends ReportingEvent> getEventsAndFlush() {
        final ImmutableList<? extends ReportingEvent> events = ImmutableList.copyOf(nonCommitedEvents);
        nonCommitedEvents.clear();
        return events;
    }

}
