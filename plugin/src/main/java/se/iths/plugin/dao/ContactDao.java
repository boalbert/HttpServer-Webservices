package se.iths.plugin.dao;

import se.iths.plugin.model.Contact;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ContactDao {

	EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("ContactUnit");

	public Contact findById(int id) {

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Contact contact = em.find(Contact.class,id);

		return contact;
	}

	public List findContact(int id)  {
		boolean contactFound = false;
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		List list = em.createQuery("from Contact c where c.id = :param1", Contact.class)
				.setParameter("param1", id)
				.getResultList();
		em.getTransaction().commit();
		return list;
	}

	public void createContact(Contact Contact) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(Contact);
		em.getTransaction().commit();
		System.out.println("Contact created");
	}

	public boolean deleteContact(int id)    {
		boolean status = false;
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Contact x = em.createQuery("from Contact c where c.id = :param1", Contact.class)
				.setParameter("param1", id)
				.getSingleResult();
		em.remove(x);
		em.getTransaction().commit();
		status = true;
		return status;
	}
}
