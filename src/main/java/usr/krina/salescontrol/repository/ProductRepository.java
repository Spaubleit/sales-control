package usr.krina.salescontrol.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import usr.krina.salescontrol.entity.Product;

@Transactional(propagation = Propagation.MANDATORY)
public interface ProductRepository extends CrudRepository<Product, Long> {
}
