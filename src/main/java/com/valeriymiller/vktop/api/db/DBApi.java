package com.valeriymiller.vktop.api.db;

import com.valeriymiller.vktop.model.Token;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * Created by valer on 21.12.2015.
 */
public class DBApi {

    public static List getToken(Integer userId) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Criteria criteria = session.createCriteria(Token.class)
                .add( Expression.like("userId", userId) ); //создаем критерий запроса
        List tokens = criteria.list(); //помещаем результаты в список

        return tokens;
    }

    public static void saveToken(Token token) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(token);
        session.getTransaction().commit();
    }
}
