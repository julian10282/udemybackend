package com.udemy.backendninja.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.udemy.backendninja.converter.ContactConverter;
import com.udemy.backendninja.entity.ContactEntity;
import com.udemy.backendninja.model.ContactModel;
import com.udemy.backendninja.repository.ContactRepository;
import com.udemy.backendninja.service.ContactService;

@Service("contactServiceImpl")
public class ContactServiceImpl implements ContactService{
	
	@Autowired
	@Qualifier("contactRepository")
	private ContactRepository contactRepository;
	
	@Autowired
	@Qualifier("contactConverter")
	private ContactConverter contactConverter;
	
	@Override
	public ContactModel addContact(ContactModel contactModel) {
		ContactEntity contactEntity = contactRepository.save(contactConverter.modelToEntity(contactModel));
		return contactConverter.entityToModel(contactEntity);
	}

	@Override
	public List<ContactModel> getAllContacts() {
		List<ContactModel> contactModels = new ArrayList<>();
		
		List<ContactEntity> contactEntities = contactRepository.findAll();
		for (ContactEntity contactEntity : contactEntities) {
			contactModels.add(contactConverter.entityToModel(contactEntity));
			
		}
		
		return contactModels;
	}

	@Override
	public ContactModel findContactById(int id) {
		return contactConverter.entityToModel(contactRepository.findById(id));
	}

	@Override
	public void removeContact(int id) {
		ContactEntity contactEntity = contactRepository.findById(id);
		if (contactEntity != null) {
			contactRepository.delete(contactEntity);
		}
	}

}
