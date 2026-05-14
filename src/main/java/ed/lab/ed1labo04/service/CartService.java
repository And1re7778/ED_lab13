package ed.lab.ed1labo04.service;

import ed.lab.ed1labo04.entity.Cart;
import ed.lab.ed1labo04.entity.CartItem;
import ed.lab.ed1labo04.entity.Product;
import ed.lab.ed1labo04.repository.CartRepository;
import ed.lab.ed1labo04.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Cart createCart(Cart request) throws IllegalArgumentException {
        Cart cart = new Cart();
        double total = 0.0;

        for (CartItem itemReq : request.getCartitems()) {
            if (itemReq.getQuantity() <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
            }

            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no existe"));

            if (product.getQuantity() < itemReq.getQuantity()) {
                throw new IllegalArgumentException("Inventario insuficiente");
            }

            product.setQuantity(product.getQuantity() - itemReq.getQuantity());
            productRepository.save(product);

            CartItem cartItem = new CartItem();
            cartItem.setProductId(product.getId());
            cartItem.setName(product.getName());
            cartItem.setPrice(product.getPrice());
            cartItem.setQuantity(itemReq.getQuantity());

            cart.getCartitems().add(cartItem);
            total += product.getPrice() * itemReq.getQuantity();
        }

        cart.setTotalPrice(total);
        return cartRepository.save(cart);
    }

    public Optional<Cart> getById(Long id) {
        return cartRepository.findById(id);
    }
}