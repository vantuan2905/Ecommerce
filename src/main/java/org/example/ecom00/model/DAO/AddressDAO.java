package org.example.ecom00.model.DAO;

import org.example.ecom00.model.Address;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface AddressDAO extends ListCrudRepository<Address,Long> {
    List<Address> findByUser_Id(Long id);
}
