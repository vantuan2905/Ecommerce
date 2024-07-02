package org.example.ecom00.service;
import org.example.ecom00.model.DAO.ProductDAO;
import org.example.ecom00.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for handling product actions.
 */
@Service
public class ProductService {

    /** The Product DAO. */
    private ProductDAO productDAO;

    /**
     * Constructor for spring injection.
     * @param productDAO
     */
    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    /**
     * Gets the all products available.
     * @return The list of products.
     */
    public List<Product> getProducts() {
        return productDAO.findAll();
    }

}
