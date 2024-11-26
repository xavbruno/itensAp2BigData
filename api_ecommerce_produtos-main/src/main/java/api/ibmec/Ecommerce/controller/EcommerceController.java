package api.ibmec.Ecommerce.controller;

import api.ibmec.Ecommerce.service.ProductService;
import api.ibmec.Ecommerce.entity.Product;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class EcommerceController {

    @Autowired
    private ProductService productService;

    // Endpoint de introdução à API
    @GetMapping("/welcome")
    public ResponseEntity<String> introducao() {
        return ResponseEntity.ok("Bem-vindo à API do nosso e-commerce!");
    }

    // Criar um novo produto
    @PostMapping
    public ResponseEntity<Product> criarProduto(@Valid @RequestBody Product product) {
        Product novoProduto = productService.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }

    // Obter todos os produtos
    @GetMapping
    public ResponseEntity<List<Product>> listarProdutos() {
        List<Product> produtos = productService.findAllProducts();
        if (produtos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(produtos);
    }

    // Buscar um produto pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> buscarProdutoPorId(@PathVariable String id) {
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


}
