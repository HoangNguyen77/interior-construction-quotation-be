package com.swp.spring.interiorconstructionquotation.service.product;

import com.swp.spring.interiorconstructionquotation.dao.ICategoryProductRepository;
import com.swp.spring.interiorconstructionquotation.dao.IProductRepository;
import com.swp.spring.interiorconstructionquotation.entity.CategoryProduct;
import com.swp.spring.interiorconstructionquotation.entity.Product;
import com.swp.spring.interiorconstructionquotation.entity.ProductImage;
import com.swp.spring.interiorconstructionquotation.entity.TypeProduct;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService{
    @Autowired
    private IProductRepository productRepository;
    private ICategoryProductRepository categoryProductRepository;

    @Override
    public Page<Product> getAllProducts(int page, int pagesize) {
        try {
            Pageable pageable = PageRequest.of(page, pagesize);
            return productRepository.findAll(pageable);
        } catch (Exception e) {
            // Log or handle unexpected exceptions
            throw new RuntimeException("Failed to retrieve products", e);
        }
    }

    @Override
    public Product getProductById(int id) {
        try {
            return productRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        } catch (EntityNotFoundException e) {
            throw e; // Re-throw EntityNotFoundException
        } catch (Exception e) {
            // Log or handle unexpected exceptions
            throw new RuntimeException("Failed to retrieve product with id: " + id, e);
        }
    }

    @Override
    public Product createProduct(Product product) {
        try {
            return productRepository.save(product);
        } catch (Exception e) {
            // Log or handle unexpected exceptions
            throw new RuntimeException("Failed to create product", e);
        }
    }


    @Override
    public Product updateProduct(int id, Product updatedProduct) {
        try {
            Product product = getProductById(id);
            // Update product properties
            product.setName(updatedProduct.getName());
            product.setWidth(updatedProduct.getWidth());
            product.setLength(updatedProduct.getLength());
            product.setHeight(updatedProduct.getHeight());
            product.setUnitPrice(updatedProduct.getUnitPrice());
            product.setUnit(updatedProduct.getUnit());
            return productRepository.save(product);
        } catch (EntityNotFoundException e) {
            throw e; // Re-throw EntityNotFoundException
        } catch (Exception e) {
            // Log or handle unexpected exceptions
            throw new RuntimeException("Failed to update product with id: " + id, e);
        }
    }

    @Override
    public void deleteProduct(int id) {
        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            // Log or handle unexpected exceptions
            throw new RuntimeException("Failed to delete product with id: " + id, e);
        }
    }

    @Override
    public Page<Product> getRelatedProductsByCategoryId(int typeId, Pageable pageable) {
        List<CategoryProduct> categoryProducts = categoryProductRepository.findByTypeRoom_RoomId(typeId);
        if (categoryProducts.isEmpty()) {
            // Handle scenario where no categoryProducts found for the given roomId
            return new PageImpl<>(Collections.emptyList());
        }

        List<Integer> typeIds = categoryProducts.stream()
                .map(categoryProduct -> categoryProduct.getTypeRoom().getRoomId())
                .collect(Collectors.toList());
        return productRepository.findByTypeProduct_TypeId(typeId, pageable);
    }


}
