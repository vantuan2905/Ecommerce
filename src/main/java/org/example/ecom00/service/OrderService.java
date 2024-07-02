package org.example.ecom00.service;

import org.example.ecom00.model.DAO.WebOrderDAO;
import org.example.ecom00.model.LocalUser;
import org.example.ecom00.model.WebOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    /** The Web Order DAO. */
    private WebOrderDAO webOrderDAO;

    /**
     * Constructor for spring injection.
     * @param webOrderDAO
     */
    public OrderService(WebOrderDAO webOrderDAO) {
        this.webOrderDAO = webOrderDAO;
    }

    /**
     * Gets the list of orders for a given user.
     * @param user The user to search for.
     * @return The list of orders.
     */
    public List<WebOrder> getOrders(LocalUser user) {
        return webOrderDAO.findByUser(user);
    }

}