package org.isegodin.expenses.adviser.telegram.processor;

import org.isegodin.expenses.adviser.telegram.bot.data.dto.UpdateDto;

/**
 * @author isegodin
 */
public interface UpdateEventProcessor {

    boolean canProcess(UpdateDto updateDto);

    boolean process(UpdateDto updateDto);
}
