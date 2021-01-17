package usr.krina.salescontrol.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usr.krina.salescontrol.entity.Wholesale;
import usr.krina.salescontrol.repository.WholesaleRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class WholesaleServiceImpl implements EntityService<Wholesale> {
    private final WholesaleRepository repository;

    @Autowired
    public WholesaleServiceImpl(WholesaleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Wholesale save(Wholesale wholesale) {
        return repository.save(wholesale);
    }

    @Override
    public void delete(Wholesale wholesale) {
        repository.delete(wholesale);
    }

    @Override
    public List<Wholesale> findAll() {
        return (List<Wholesale>) repository.findAll();
    }
}
