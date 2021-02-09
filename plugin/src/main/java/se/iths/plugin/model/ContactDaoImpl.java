package se.iths.plugin.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ContactDaoImpl {

	EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("ContactUnit");

	public Contact findById(int id) {



		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Contact contact = em.find(Contact.class,id);

		return contact;

	}
}
