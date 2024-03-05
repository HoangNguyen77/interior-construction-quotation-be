package com.swp.spring.interiorconstructionquotation.service.product.typeProduct;

import com.swp.spring.interiorconstructionquotation.entity.TypeProduct;

public interface ITypeProductService {
    TypeProduct createTypeProduct(TypeProduct typeProduct);

    TypeProduct getTypeProductByName(String typeName);

}
