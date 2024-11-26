package api.ibmec.Ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import api.ibmec.Ecommerce.entity.Product;
import api.ibmec.Ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azure.cosmos.models.PartitionKey;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Listar todos os produtos
    public List<Product> findAllProducts() {
        List<Product> productList = new ArrayList<>();
        productRepository.findAll().forEach(productList::add); // Conversão de Iterable para List
        return productList;
    }

    // Buscar produtos pelo nome
    public List<Product> findProductByName(String productName) {
        return productRepository.findByProductName(productName);
    }

    // Buscar produto pelo ID
    public Optional<Product> findById(String productId) {
        return productRepository.findById(productId);
    }

    // Salvar novo produto
    public Product save(Product product) {
        product.setProductid(UUID.randomUUID().toString());
        return productRepository.save(product);
    }

    // Excluir produto pelo ID
    public void delete(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado para exclusão"));

        productRepository.deleteById(productId, new PartitionKey(product.getProductCategory()));
    }
}
