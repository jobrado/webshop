package hr.algebra.webshop.dto;

import hr.algebra.webshop.controller.ProductController;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CartProduct {
    CartDTO cartDTO;
    ProductDTO productDTO;
}
