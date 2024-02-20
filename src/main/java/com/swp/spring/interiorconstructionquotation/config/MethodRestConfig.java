package com.swp.spring.interiorconstructionquotation.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
@Configuration
public class MethodRestConfig implements RepositoryRestConfigurer {
    @Autowired
    private EntityManager entityManager;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        //config return id
        config.exposeIdsFor(entityManager.getMetamodel().getEntities().stream().map(Type::getJavaType).toArray(Class[]::new));
        //config cors
//        cors.addMapping("/**")
//                .allowedOrigins(url)
//                .allowedMethods("GET", "POST", "PUT", "DELETE");
//
//        //config disable
//        HttpMethod[] chanCacPhuongThuc = {
//                HttpMethod.POST,
//                HttpMethod.PATCH,
//                HttpMethod.PUT,
//                HttpMethod.DELETE
//        };
//        disableHttpMethods(TheLoai.class, config, chanCacPhuongThuc);
    }
//    private void disableHttpMethods(Class c, RepositoryRestConfiguration config, HttpMethod[] methods){
//        config.getExposureConfiguration()
//                .forDomainType(c)
//                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(methods))
//                .withCollectionExposure(((metdata, httpMethods) -> httpMethods.disable(methods)));
//
//    }
}
