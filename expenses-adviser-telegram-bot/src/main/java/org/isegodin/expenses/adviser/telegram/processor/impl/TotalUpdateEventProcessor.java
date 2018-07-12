package org.isegodin.expenses.adviser.telegram.processor.impl;

import org.isegodin.expenses.adviser.telegram.bot.data.dto.UpdateDto;
import org.isegodin.expenses.adviser.telegram.processor.UpdateEventProcessor;
import org.springframework.stereotype.Component;

/**
 * TODO
 * @author isegodin
 */
@Component
public class TotalUpdateEventProcessor implements UpdateEventProcessor{

    @Override
    public boolean canProcess(UpdateDto updateDto) {
        return false;
    }

    @Override
    public boolean process(UpdateDto updateDto) {
        return false;
    }
}
