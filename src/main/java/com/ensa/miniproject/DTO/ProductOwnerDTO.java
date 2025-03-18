package com.ensa.miniproject.DTO;

import com.ensa.miniproject.entities.ProductOwner;
import com.ensa.miniproject.entities.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductOwnerDTO extends UserDTO{
    List<Project> projects;

    public ProductOwner toEntity(){
        ProductOwner productOwner = new ProductOwner();
        productOwner.setId(this.getId());
        productOwner.setUsername(this.getUsername());
        productOwner.setPassword(this.getPassword());
        productOwner.setEmail(this.getEmail());
        productOwner.setProjects(this.getProjects());
        productOwner.setPrenom(this.getPrenom());
        return productOwner;
    }

    public static ProductOwnerDTO fromEntity(ProductOwner productOwner){
        ProductOwnerDTO productOwnerDTO = new ProductOwnerDTO();
        productOwnerDTO.setId(productOwner.getId());
        productOwnerDTO.setUsername(productOwner.getUsername());
        productOwnerDTO.setPassword(productOwner.getPassword());
        productOwnerDTO.setEmail(productOwner.getEmail());
        productOwnerDTO.setProjects(productOwner.getProjects());
        productOwnerDTO.setPrenom(productOwner.getPrenom());
        return productOwnerDTO;
    }
}
