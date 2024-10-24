package com.example.crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;

import com.example.crud.dto.ProductDTO;
import com.example.crud.exceptions.ProductCreateExceptions;
import com.example.crud.exceptions.ProductDeletedException;
import com.example.crud.exceptions.ProductExceptions;
import com.example.crud.exceptions.ProductNotFoundExceptions;
import com.example.crud.model.ProductModel;
import com.example.crud.repository.ProductRepository;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository repository;

    // Listar todos os produtos
    public List<ProductModel> getAllProducts() {
        return repository.findAllOrderedById();
    }

    // Pesquisar produto pelo nome
    public Optional<ProductModel> searchProductByName(String name) {

        Optional<ProductModel> foundProduct = repository.findByName(name);

        if (foundProduct.isEmpty()) {
            throw new ProductExceptions("Não existe nenhum produto com o nome: " + name);
        }
        return foundProduct;
        
    }

    // Pesquisar produto pelo ID
    public Optional<ProductModel> searchProductById(Long id) {

        Optional<ProductModel> foundProduct = repository.findById(id);

        if (foundProduct.isEmpty()) {
            throw new ProductExceptions("Não existe nenhum produto com o id: " + id);
        } else {
            return foundProduct;
        }
        
    }

    // Verificar se o produto existe
    public boolean productExist(String name) {
        return repository.existsByName(name);
    }

    // Criar novo produto
    public ProductModel createNewProduct(ProductDTO data) throws Exception {

        if (!data.name().isBlank() && data.price_in_cents() != null) {

            if(!productExist(data.name())) {


                
                try {
        
                    ProductModel newProduct = new ProductModel(data);
                    return repository.save(newProduct);
                    
                } catch (Exception ex) {
                    throw new ProductExceptions("Não foi possivel criar o produto", ex);
                }
            } else {
                throw new ProductExceptions("Já Existe um produto cadastrado com este nome!");
            }

        } else {
            throw new ProductExceptions("Não foi possivel criar o produto falta dados");
        }
    }

    // Atualizar produto
    public Optional<ProductModel> updateProduct(Long id, ProductDTO data) throws Exception {

        Optional<ProductModel> optionalProduct = repository.findById(id);

        if (optionalProduct.isPresent()) {
            ProductModel product = optionalProduct.get();
            
            boolean productExist = productExist(data.name());
            if (productExist) {
                throw new ProductExceptions("Já existe um produto com o nome: " + data.name() + " registrado!");
            } else {
                
                if (data.name().isBlank() || data.price_in_cents() == null) {

                    throw new ProductExceptions("Não foi possível atualizar o produto. Faltam dados obrigatórios");

                } else {
                    product.setName(data.name());
                    product.setPrice_in_cents(data.price_in_cents());
                }
                
                repository.save(product);

                return Optional.of(product);
            }

        } else {
            throw new ProductExceptions("Não encontramos o produto com o id: " + id + " para atualizar");
        }
    }

    // Deletar produto
    public ProblemDetail deleteProductById(Long id) throws Exception {
        
        Optional<ProductModel> Optionalproduct = repository.findById(id);

        if (Optionalproduct.isPresent()) {
            repository.deleteById(id);
            throw new ProductDeletedException();
        } else {
            throw new ProductNotFoundExceptions("Não encontramos o produto com o id: " + id + " para deletar");
        }

    }

}
