package com.ufps.gidisoft.requests.formats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormatsFilter {
    private Long directorId;
    private Long groupId;
    private Long academicPeriodId;
    private Long status;
}
