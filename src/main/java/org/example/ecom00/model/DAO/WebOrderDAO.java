package org.example.ecom00.model.DAO;

import org.example.ecom00.model.LocalUser;
import org.example.ecom00.model.WebOrder;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface WebOrderDAO extends ListCrudRepository<WebOrder, Long> {

    List<WebOrder> findByUser(LocalUser user);

}