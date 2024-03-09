package com.swp.spring.interiorconstructionquotation.service.product;

import com.swp.spring.interiorconstructionquotation.dao.*;
import com.swp.spring.interiorconstructionquotation.entity.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService{

    private IProductRepository productRepository;
    private ICategoryProductRepository categoryProductRepository;
    private IUnitRepostitory unitRepostitory;
    private ITypeProductRepository typeProductRepository;
    private IProductImageRepository productImageRepository;
    @Autowired
    public ProductService(IProductRepository productRepository, ICategoryProductRepository categoryProductRepository, IUnitRepostitory unitRepostitory, ITypeProductRepository typeProductRepository, IProductImageRepository productImageRepository) {
        this.productRepository = productRepository;
        this.categoryProductRepository = categoryProductRepository;
        this.unitRepostitory = unitRepostitory;
        this.typeProductRepository = typeProductRepository;
        this.productImageRepository = productImageRepository;
    }





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
    @Transactional
    public ResponseEntity<?> createProduct(ProductRequest productRequest) {
            try {
                if (categoryProductRepository.findByCategoryId(productRequest.getCategoryId()) != null){
                    CategoryProduct categoryProduct = categoryProductRepository.findByCategoryId(productRequest.getCategoryId());
                    TypeProduct typeProduct = new TypeProduct();
                    Product product = new Product();

                    Unit unit = unitRepostitory.findByUnitId(productRequest.getUnitId());
                    //
                    typeProduct.setTypeName(productRequest.getTypeName());
                    typeProduct.setCategoryProduct(categoryProduct);
                    TypeProduct createdTypeProduct = typeProductRepository.save(typeProduct);
                    product.setName(productRequest.getName());
                    product.setWidth(productRequest.getWidth());
                    product.setLength(productRequest.getLength());
                    product.setHeight(productRequest.getHeight());
                    product.setUnitPrice(productRequest.getUnitPrice());
                    product.setUnit(unit);
                    product.setTypeProduct(createdTypeProduct);
                    Product createdProduct = productRepository.save(product);
                    //blog image
                    for (String image : productRequest.getProductImageList()){
                        ProductImage productImage = new ProductImage();
                        productImage.setProduct(createdProduct);
                        productImage.setImageData(image);
                        ProductImage createdProductImage = productImageRepository.save(productImage);
                    }
                    return ResponseEntity.ok("Success");
                }else{
                    return ResponseEntity.badRequest().body("Fail");
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
                return ResponseEntity.badRequest().body("Fail");
            }
        }



    @Override
    @Transactional
    public ResponseEntity<?> updateProduct(ProductRequest productRequest) {
        try {
            // Retrieve the existing product
            Product product = productRepository.findByProductId(productRequest.getProductId());
            if (product == null) {
                return ResponseEntity.badRequest().body("Product not found");
            }
            TypeProduct typeProduct = product.getTypeProduct();
            CategoryProduct categoryProduct = typeProduct.getCategoryProduct();
            CategoryProduct newCategoryProduct = categoryProductRepository.findByCategoryId(productRequest.getCategoryId());
            Unit unit = unitRepostitory.findByUnitId(productRequest.getUnitId());


            //Update product
            typeProduct.setTypeName(productRequest.getTypeName());
            typeProduct.setCategoryProduct(newCategoryProduct);
            TypeProduct createdTypeProduct = typeProductRepository.saveAndFlush(typeProduct);
            product.setName(productRequest.getName());
            product.setWidth(productRequest.getWidth());
            product.setLength(productRequest.getLength());
            product.setHeight(productRequest.getHeight());
            product.setUnitPrice(productRequest.getUnitPrice());
            product.setUnit(unit);
            product.setTypeProduct(createdTypeProduct);
            Product updatedProduct = productRepository.saveAndFlush(product);

            List<ProductImage> existingImages = productImageRepository.findByProduct_ProductId(updatedProduct.getProductId());
            productImageRepository.deleteAll(existingImages);

            //save new image
            for (String imageData: productRequest.getProductImageList()){
                ProductImage newImage = new ProductImage();
                newImage.setProduct(product);
                newImage.setImageData(imageData);
                productImageRepository.save(newImage);
            }
            return ResponseEntity.ok().body("product updated successfully");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Update failed due to an error: " + e.getMessage());
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
