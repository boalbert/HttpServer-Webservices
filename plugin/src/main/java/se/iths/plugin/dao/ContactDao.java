package se.iths.plugin.dao;

import se.iths.plugin.model.Contact;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ContactDao {


	EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("ContactUnit");

	public Contact findById(int id) {

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		return em.find(Contact.class, id);
	}

	public void createContact(Contact Contact) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(Contact);
		em.getTransaction().commit();
	}
}
