package org.isegodin.expenses.adviser.backend.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author isegodin
 */
@Data
@NoArgsConstructor
public class PageData {

    int page;
    int size;
    boolean asc = true;
    String sortField;

    public PageData(int page, int size, String sortField) {
        this.page = page;
        this.size = size;
        this.sortField = sortField;
    }
}
