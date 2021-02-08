package se.iths.plugin;

import se.iths.spi.IOhandler;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class DatabaseIMPL implements IOhandler {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("DatabaseJPA");

    @Override
    public String urlHandler(String parsedURL) {
        String finalString = "";

        return finalString;
    }

    public List findContact(int id)  {
        boolean contactFound = false;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List list = em.createQuery("from Contact c where c.id = :param1", ContactModel.class)
                .setParameter("param1", id)
                .getResultList();
        em.getTransaction().commit();
        return list;
    }

    public void createContact(ContactModel contactModel) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(contactModel);
        em.getTransaction().commit();
        System.out.println("Contact created");
    }

    public boolean deleteContact(int id)    {
        boolean status = false;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        ContactModel x = em.createQuery("from Contact c where c.id = :param1", ContactModel.class)
                    .setParameter("param1", id)
                    .getSingleResult();
        em.remove(x);
        em.getTransaction().commit();
        status = true;
        return status;
    }

}
