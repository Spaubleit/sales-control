package usr.krina.salescontrol.service;

import usr.krina.salescontrol.entity.Product;
import usr.krina.salescontrol.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ContactRepository repository;

    @Autowired
    public ProductServiceImpl(ContactRepository repository) {
        this.repository = repository;
    }

    /**
     * Метод добавляет парочку записей в БД после запуска приложения,
     * чтобы не было совсем пусто.
     *
     * Из-за того, что подключена H2 (in-memory) БД.
     */
    @PostConstruct
    public void generateTestData() {
        save(new Product("Иван Иванов", 5.6, 10));
        save(new Product("Петр Петров", 3.2, 7.8));
    }

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }
}
