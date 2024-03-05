package com.swp.spring.interiorconstructionquotation.service.product.typeProduct;

import com.swp.spring.interiorconstructionquotation.dao.ITypeProductRepository;
import com.swp.spring.interiorconstructionquotation.entity.TypeProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TypeProductService implements ITypeProductService{
    private ITypeProductRepository typeProductRepository;
    @Autowired
    public TypeProductService(ITypeProductRepository typeProductRepository){
        this.typeProductRepository = typeProductRepository;
    }


    @Override
    public TypeProduct createTypeProduct(TypeProduct typeProduct) {
        try{
            return typeProductRepository.save(typeProduct);
        }catch (Exception e){
            throw new RuntimeException("Loi xay ra khi them typeRoom: ",e);
        }
    }

    @Override
    public TypeProduct getTypeProductByName(String typeName) {
        try {
            return typeProductRepository.findByTypeName(typeName);
        } catch (Exception e) {
            // Handle specific exceptions or log the error
            throw new RuntimeException("Failed to get TypeProduct by name", e);
        }
    }
}
