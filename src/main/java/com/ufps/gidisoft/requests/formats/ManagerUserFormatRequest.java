package com.ufps.gidisoft.requests.formats;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NotNull
public class ManagerUserFormatRequest {

    private String createdBy;
    private String reviewBy;
    private String approveBy;
}
