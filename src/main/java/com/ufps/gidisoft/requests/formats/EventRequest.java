package com.ufps.gidisoft.requests.formats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {

    private Long id;
    private String name;
    private LocalDate createdAt;
    private Integer compliancePercentage;
    private Long formatId;
    private List<MultipartFile> files = new ArrayList<>();
}
