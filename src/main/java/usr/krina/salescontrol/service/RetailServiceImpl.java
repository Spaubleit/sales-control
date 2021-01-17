package usr.krina.salescontrol.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usr.krina.salescontrol.entity.Retail;
import usr.krina.salescontrol.repository.RetailRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RetailServiceImpl implements EntityService<Retail> {
    private final RetailRepository repository;

    @Autowired
    public RetailServiceImpl(RetailRepository repository) {
        this.repository = repository;
    }

    @Override
    public Retail save(Retail retail) {
        return repository.save(retail);
    }

    @Override
    public void delete(Retail retail) {
        repository.delete(retail);
    }

    @Override
    public List<Retail> findAll() {
        return (List<Retail>) repository.findAll();
    }
}
