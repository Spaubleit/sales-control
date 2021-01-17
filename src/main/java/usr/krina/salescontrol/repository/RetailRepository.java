package usr.krina.salescontrol.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import usr.krina.salescontrol.entity.Retail;

@Transactional(propagation = Propagation.MANDATORY)
public interface RetailRepository extends CrudRepository<Retail, Long> {
}
