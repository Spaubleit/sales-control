package usr.krina.salescontrol.service;

import usr.krina.salescontrol.entity.Product;

import java.util.List;

public interface ProductService {

    Product save(Product product);

    List<Product> findAll();

}
