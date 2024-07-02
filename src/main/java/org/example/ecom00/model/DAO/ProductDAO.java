package org.example.ecom00.model.DAO;

import org.example.ecom00.model.LocalUser;
import org.example.ecom00.model.Product;
import org.example.ecom00.model.WebOrder;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

/**
 * Data Access Object to access WebOrder data.
 */
public interface ProductDAO extends ListCrudRepository<Product, Long> {
}