package com.ensa.miniproject.DTO;

import com.ensa.miniproject.entities.Epic;
import com.ensa.miniproject.entities.ProductBacklog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EpicDTO {
    private Long id;
    private String title;
    private String description;
    private ProductBacklog productBacklog;

}
