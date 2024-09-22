package com.example.warehouse.service;

import com.example.warehouse.model.Product;
import com.example.warehouse.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {

  /*
  Implement the business logic for the ProductService operations in this class
  Make sure to add required annotations
  */

  @Autowired
  private ProductRepository productRepository;


  //to post all the Product details
  //created->201
  //badRequest->400
  public Object postProduct(Product product) throws DataIntegrityViolationException{
  	Product p = productRepository.findBySku(product.getSku());
  	if(p==null) {
  		Product p1 = productRepository.saveAndFlush(product);
  		if(p1!=null) {
  			return new ResponseEntity<>(p1,HttpStatus.CREATED);
  		}
  		
  	}
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  //to get all the Product details
  //ok->200
  //badRequest->400
  public Object getProduct() throws DataIntegrityViolationException{
  	List<Product> pl= null;
  	pl=productRepository.findAll();
  	if(pl!=null && !pl.isEmpty()) {
  		new ResponseEntity<>(pl,HttpStatus.OK);
  	}
    return new ResponseEntity<>(pl,HttpStatus.BAD_REQUEST);
  }

  //to get the product with the value(pathVariable)
  //ok()->200
  //badRequest()->400
  public ResponseEntity<Object> getSimilarVendor(String value) throws DataIntegrityViolationException{
  	List<Product> p = productRepository.findByVendor(value);
  	if(p!=null) {
  		return new ResponseEntity<>(p,HttpStatus.OK);
  	}
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }


  //to update the Product with id as pathVariable and Product as object in RequestBody
  //ok->200
  //badRequest->400
  public ResponseEntity<Object> updateProduct(int id, Product product) throws DataIntegrityViolationException{
  	Optional<Product> p = productRepository.findById(id);
  	if(p.isPresent()) {
  		Product p1 = p.get();
  		Product p2 = productRepository.findBySku(p1.getSku());
  		if(p2==null) {
	  		p1.setCurrency(product.getCurrency());
	  		p1.setDescription(product.getDescription());
	  		p1.setImage_url(product.getImage_url());
	  		p1.setName(product.getName());
	  		p1.setPrice(product.getPrice());
	  		p1.setStock(product.getStock());
	  		p1.setVendor(product.getVendor());
	  		productRepository.saveAndFlush(p1);
	  		return new ResponseEntity<>(HttpStatus.OK);
	  		
  		}
  		
  	}
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

  // to delete the product by using id as PathVariable
  //ok->200
  //badRequest->400
  public ResponseEntity<Object> deleteProductById(int id) throws DataIntegrityViolationException{
  	Optional<Product> p = productRepository.findById(id);
  	if(p.isPresent() && p!=null) {
  		productRepository.delete(p.get());
  		new ResponseEntity<>(HttpStatus.OK);
  	}
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }



}
