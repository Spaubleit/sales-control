package usr.krina.salescontrol.service;

import usr.krina.salescontrol.entity.Contact;

import java.util.List;

public interface ContactService {

    Contact save(Contact contact);

    List<Contact> findAll();

}
