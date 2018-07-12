package org.isegodin.expenses.adviser.telegram.service.impl;

import org.isegodin.expenses.adviser.telegram.bot.data.dto.UpdateDto;
import org.isegodin.expenses.adviser.telegram.data.dict.UpdateEventStatus;
import org.isegodin.expenses.adviser.telegram.data.dto.UpdateEventDto;
import org.isegodin.expenses.adviser.telegram.data.filter.UpdateEventFilter;
import org.isegodin.expenses.adviser.telegram.processor.UpdateEventProcessor;
import org.isegodin.expenses.adviser.telegram.service.JsonService;
import org.isegodin.expenses.adviser.telegram.service.UpdateEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author isegodin
 */
@Component
public class UpdateEventProcessorService {

    private final UpdateEventService updateEventService;

    private final JsonService jsonService;

    private final List<UpdateEventProcessor> processors;

    @Autowired
    public UpdateEventProcessorService(UpdateEventService updateEventService, JsonService jsonService, List<UpdateEventProcessor> processors) {
        this.updateEventService = updateEventService;
        this.jsonService = jsonService;
        this.processors = processors;
    }

    @PostConstruct
    public void postConstruct() {
        new PollingThread().start();
    }

    private void processEvents(Collection<UpdateEventDto> events) {
        for (UpdateEventDto event : events) {
            UpdateDto updateDto = jsonService.fromJson(event.getRawUpdate(), UpdateDto.class);

            UpdateEventProcessor processor = null;

            for (UpdateEventProcessor p : processors) {
                if (p.canProcess(updateDto)) {
                    if (processor == null) {
                        processor = p;
                    } else {
                        throw new IllegalStateException("Ambiguous update event, can be processed by multiple processors: " + p.getClass() + ", " + processor.getClass() + ", " + event);
                    }
                }
            }

            if (processor == null) {
                event.setStatus(UpdateEventStatus.UNKNOWN);
                updateEventService.save(event);
                continue;
            }

            try {
                boolean processed = processor.process(updateDto);
                if (processed) {
                    event.setStatus(UpdateEventStatus.PROCESSED);
                    updateEventService.save(event);
                } else {
                    event.setStatus(UpdateEventStatus.PENDING);
                    updateEventService.save(event);
                }
            } catch (Exception e) {
                StringWriter writer = new StringWriter();
                e.printStackTrace(new PrintWriter(writer));

                event.setStatus(UpdateEventStatus.ERROR);
                event.setErrorDescription(writer.toString());
                updateEventService.save(event);
            }
        }
    }


    private class PollingThread extends Thread {

        @Override
        public void run() {
            while (!interrupted()) {
                PageRequest pageRequest = PageRequest.of(1, 10, Sort.by(Sort.Direction.ASC, "eventDate"));
                UpdateEventFilter filter = UpdateEventFilter.builder()
                        .statuses(Arrays.asList(UpdateEventStatus.NEW, UpdateEventStatus.PENDING))
                        .build();

                Page<UpdateEventDto> page = updateEventService.listByFilter(filter, pageRequest);

                processEvents(page.getContent());

                synchronized (this) {
                    try {
                        wait(TimeUnit.SECONDS.toMillis(2));
                    } catch (InterruptedException e) {
                        // nothing to do
                    }
                }
            }
        }
    }

}
