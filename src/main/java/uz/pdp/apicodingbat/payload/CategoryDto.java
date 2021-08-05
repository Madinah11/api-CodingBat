package uz.pdp.apicodingbat.payload;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {
    private String name;
    private String description;
    private List<Integer> languageId;

}
