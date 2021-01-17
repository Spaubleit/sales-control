package usr.krina.salescontrol.service;

import usr.krina.salescontrol.entity.Contractor;

import java.util.List;

public interface EntityService<T> {
    T save(T entity);
    void delete(T entity);
    List<T> findAll();
}
