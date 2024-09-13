package com.devsuperior.dscatalog.repositories;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
class ProductRepositoryTest {

    private long existingId;
    private long nonExistingId;
    private long countTotalProducts;


    @Autowired
    private ProductRepository repository;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 100L;
        countTotalProducts = 25L;
    }


    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){

        // arrange

        // action
        repository.deleteById(existingId);

        // assert
        Optional<Product> result = repository.findById(existingId);
        Assertions.assertFalse(result.isPresent());

    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull(){
        Product product = Factory.createProduct();
        product.setId(null);

        product = repository.save(product);

        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(countTotalProducts + 1, product.getId());
    }

    /* findById deveria
     ◦ retornar um Optional<Product> não vazio quando o id existir
     ◦ retornar um Optional<Product> vazio quando o id não existir */

    @Test
    public void returnOptionalNoEmptyWhenIdExist(){

        Optional<Product> result = repository.findById(existingId);

        Assertions.assertFalse(result.isEmpty());

    }

    @Test
    public void returnOptionalEmptyWhenIdNoExist(){

        Optional<Product> result = repository.findById(nonExistingId);

        Assertions.assertTrue(result.isEmpty());

    }

}