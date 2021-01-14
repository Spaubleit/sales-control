package usr.krina.salescontrol.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import usr.krina.salescontrol.entity.Product;

import java.util.List;

@Transactional(propagation = Propagation.MANDATORY)
public interface ContactRepository extends CrudRepository<Product, Long> {

    List<Product> findAll();

}
