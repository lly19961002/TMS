package tms.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class MonitorBaseDao extends BaseDao{
    @Autowired
    private SessionFactory sessionFactory;

//    @Override
//    protected Session getCurrentSession() {
//        Session ss = sessionFactory.getCurrentSession();;
//        return ss;
//    }

    @Override
    protected Session openSession() {
        Session ss = sessionFactory.openSession();
        return ss;
    }


}
