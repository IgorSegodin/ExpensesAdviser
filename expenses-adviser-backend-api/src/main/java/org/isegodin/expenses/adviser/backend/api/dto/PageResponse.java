package org.isegodin.expenses.adviser.backend.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author isegodin
 */
@Data
@NoArgsConstructor
public class PageResponse<T> {

    List<T> data;

    long totalCount;

    public PageResponse(List<T> data, long totalCount) {
        this.data = data;
        this.totalCount = totalCount;
    }
}
