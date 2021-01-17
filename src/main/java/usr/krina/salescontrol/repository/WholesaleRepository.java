package usr.krina.salescontrol.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import usr.krina.salescontrol.entity.Wholesale;

@Transactional(propagation = Propagation.MANDATORY)
public interface WholesaleRepository extends CrudRepository<Wholesale, Long> {
}
