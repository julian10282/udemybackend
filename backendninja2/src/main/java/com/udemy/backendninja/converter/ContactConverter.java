package com.udemy.backendninja.converter;

import org.springframework.stereotype.Component;

import com.udemy.backendninja.entity.ContactEntity;
import com.udemy.backendninja.model.ContactModel;

// TODO: Auto-generated Javadoc
/**
 * The Class ContactConverter.
 */
@Component("contactConverter")
public class ContactConverter {
	
	/**
	 * Model to entity.
	 *
	 * @param contactModel the contact model
	 * @return the contact entity
	 */
	public ContactEntity modelToEntity(ContactModel contactModel) {
		ContactEntity contactEntity = new ContactEntity();
		contactEntity.setCity(contactModel.getCity());
		contactEntity.setFirstname(contactModel.getFirstname());
		contactEntity.setId(contactModel.getId());
		contactEntity.setLastname(contactModel.getLastname());
		contactEntity.setTelephone(contactModel.getTelephone());
		return contactEntity;
	}
	
	/**
	 * Entity to model.
	 *
	 * @param contactEntity the contact entity
	 * @return the contact model
	 */
	public ContactModel entityToModel(ContactEntity contactEntity) {
		ContactModel contactModel = new ContactModel();
		contactModel.setCity(contactEntity.getCity());
		contactModel.setFirstname(contactEntity.getFirstname());
		contactModel.setId(contactEntity.getId());
		contactModel.setLastname(contactEntity.getLastname());
		contactModel.setTelephone(contactEntity.getTelephone());
		return contactModel;
	}

}
