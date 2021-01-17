package usr.krina.salescontrol.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usr.krina.salescontrol.entity.Contractor;
import usr.krina.salescontrol.repository.ContractorRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ContractorServiceImpl implements EntityService<Contractor> {
    private final ContractorRepository repository;

    @Autowired
    public ContractorServiceImpl(ContractorRepository repository) {
        this.repository = repository;
    }

    @Override
    public void delete(Contractor contractor) {
        repository.delete(contractor);
    }

    @Override
    public Contractor save(Contractor contractor) {
        return repository.save(contractor);
    }

    @Override
    public List<Contractor> findAll() {
        return (List<Contractor>) repository.findAll();
    }
}
