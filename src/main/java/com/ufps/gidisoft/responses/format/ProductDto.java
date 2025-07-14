package com.ufps.gidisoft.responses.format;

import com.ufps.gidisoft.entities.formats.products.Product;
import com.ufps.gidisoft.enums.utils.DateFormatEnum;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class ProductDto {

    private Long id;
    private String description;
    private LocalDate date;
    private String dateFormatter;
    private String responsibles;
    private Long formatId;
    private Long createdBy;
    private String createdByName;
    private List<String> editorsNames;
    private List<String> files;
    private Long productType;
    private String productName;

    public ProductDto(Product product, List<String> editorsNames) {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern(DateFormatEnum.DD_MM_YYYY.getValue());
        this.id = product.getId();
        this.description = product.getDescription();
        this.date = product.getDate();
        this.dateFormatter = product.getDate().format(formatters);
        this.responsibles = product.getResponsibles();
        this.formatId = product.getFormat().getId();
        this.createdBy = product.getCreatedBy().getId();
        this.createdByName = product.getCreatedBy().getName();
        this.editorsNames = editorsNames;
        this.files = product.getFiles();
        this.productType = product.getProductType().getId();
        this.productName = product.getProductType().getName();
    }
}
