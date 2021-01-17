package usr.krina.salescontrol.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usr.krina.salescontrol.entity.Product;
import usr.krina.salescontrol.repository.ProductRepository;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class ProductServiceImpl implements EntityService<Product> {

    private final ProductRepository repository;

    @Autowired
    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void delete(Product product) {
        repository.delete(product);
    }

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return (List<Product>)repository.findAll();
    }
}
