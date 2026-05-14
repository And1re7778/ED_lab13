package ed.lab.ed1labo04.service;

import ed.lab.ed1labo04.entity.Product;
import ed.lab.ed1labo04.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public Product createProduct(Product product) {
        product.setQuantity(0);
        return repository.save(product);
    }

    public List<Product> getAll() { return repository.findAll(); }

    public Optional<Product> getById(Long id) { return repository.findById(id); }

    public Product updateProduct(Product product) { return repository.save(product); }
}