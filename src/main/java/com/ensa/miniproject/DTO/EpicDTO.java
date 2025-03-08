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

    public Epic toEntity(){
        Epic epic = new Epic();
        epic.setId(id);
        epic.setTitle(title);
        epic.setDescription(description);
        epic.setProductBacklog(productBacklog);
        return epic;
    }

    public static EpicDTO fromEntity(Epic epic){
        EpicDTO epicDTO = new EpicDTO();
        epicDTO.setId(epic.getId());
        epicDTO.setTitle(epic.getTitle());
        epicDTO.setDescription(epic.getDescription());
        epicDTO.setProductBacklog(epic.getProductBacklog());
        return epicDTO;
    }
}
