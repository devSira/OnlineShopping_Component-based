/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Siraphob.B
 */
public class ShoppingcartTable {

    public static List<Shoppingcart> findAllShoppingcart() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OnlineShoppingPU");
        EntityManager em = emf.createEntityManager();
        List<Shoppingcart> cartList = null;
        try {
            cartList = (List<Shoppingcart>) em.createNamedQuery("Shoppingcart.findAll").getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);

        } finally {
            em.close();
            emf.close();
        }
        return cartList;
    }

    public static Shoppingcart findShoppingcartById(int id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OnlineShoppingPU");
        EntityManager em = emf.createEntityManager();
        Shoppingcart cart = null;
        try {
            cart = em.find(Shoppingcart.class, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            em.close();
            //emf.close();
        }
        return cart;
    }

    public static int findLastShoppingcartId() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OnlineShoppingPU");
        EntityManager em = emf.createEntityManager();
        try {
            // Use JPQL to find the maximum (most recent) ID in the Shoppingcart table
            Query query = em.createQuery("SELECT MAX(s.shoppingcartPK.cartId) FROM Shoppingcart s");
            Integer lastId = (Integer) query.getSingleResult();

            if (lastId != null) {
                return lastId;
            } else {
                // Handle the case where there are no records in the table
                return 0; // You can choose a meaningful default value
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

//    public static int updateShoppingcart(Shoppingcart cart) {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OnlineShoppingPU");
//        EntityManager em = emf.createEntityManager();
//        try {
//            em.getTransaction().begin();
//            Shoppingcart target = em.find(Shoppingcart.class, cart.getShoppingcartPK());
//            if (target == null) {
//                return 0;
//            }
//            target.setName(cart.getName());
//            target.setSalary(cart.getSalary());
//            em.persist(target);
//            em.getTransaction().commit();
//        } catch (Exception e) {
//            em.getTransaction().rollback();
//            
//        }
//        finally {
//            em.close();
//            emf.close();
//        }
//        return 1;
//        
//    }
    public static int removeShoppingcart(int id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OnlineShoppingPU");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Shoppingcart target = em.find(Shoppingcart.class, id);
            if (target == null) {
                return 0;
            }
            em.remove(target);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();

        } finally {
            em.close();
            emf.close();
        }
        return 1;
    }

    public static int insertShoppingcart(Shoppingcart cart) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OnlineShoppingPU");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Shoppingcart target = em.find(Shoppingcart.class, cart.getShoppingcartPK());
            if (target != null) {
                return 0;
            }
            em.persist(cart);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();

        } finally {
            em.close();
            emf.close();
        }
        return 1;
    }
}
