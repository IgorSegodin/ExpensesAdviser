package org.isegodin.expenses.adviser.telegram.bot.data.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author isegodin
 */
@NoArgsConstructor
@Data
public class StatusResponse<T> {

    boolean ok;
    String description;
    List<T> result;
}
