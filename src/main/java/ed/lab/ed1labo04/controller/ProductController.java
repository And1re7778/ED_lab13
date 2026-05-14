package ed.lab.ed1labo04.controller;

import ed.lab.ed1labo04.entity.Product;
import ed.lab.ed1labo04.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Product product) {
        if (product.getPrice() == null || product.getPrice() <= 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<Product> product = productService.getById(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Product productReq) {
        if (productReq.getPrice() == null || productReq.getPrice() <= 0 ||
                productReq.getQuantity() == null || productReq.getQuantity() < 0) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Product> existingOpt = productService.getById(id);
        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product existing = existingOpt.get();
        existing.setPrice(productReq.getPrice());
        existing.setQuantity(productReq.getQuantity());

        return ResponseEntity.ok(productService.updateProduct(existing));
    }
}