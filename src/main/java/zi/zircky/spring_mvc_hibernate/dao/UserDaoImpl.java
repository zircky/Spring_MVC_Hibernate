package zi.zircky.spring_mvc_hibernate.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import zi.zircky.spring_mvc_hibernate.model.User;

import java.util.List;
import java.util.logging.Logger;

@Repository
public class UserDaoImpl implements UserDao {

    private static final Logger logger = Logger.getLogger(UserDaoImpl.class.getName());

    @PersistenceContext
    private final EntityManager em;

    public UserDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<User> getAllUsers() {
        TypedQuery<User> query = em.createQuery("FROM User", User.class);
        return query.getResultList();
    }

    @Override
    public void save(User user) {
        em.persist(user);
    }

    @Override
    public void update(User user) {
        em.merge(user);
    }

    @Override
    public void delete(Long id) {
        User user = findById(id);
        if (user == null) {
            logger.warning("Trying to delete not existent user. User is null");
            return;
        }
        em.remove(findById(id));
    }

    @Override
    public User findById(Long id) {
        return em.find(User.class, id);
    }
}
