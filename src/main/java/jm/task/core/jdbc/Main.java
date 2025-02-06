/* В методе main класса Main должны происходить следующие операции:

1. Создание таблицы User(ов)
2. Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль (User с именем – name добавлен в базу данных)
3. Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
4. Очистка таблицы User(ов)
5. Удаление таблицы
*/



package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import static jm.task.core.jdbc.util.Util.closeConnection;


public class Main {
    // реализуйте алгоритм

    public static void main(String[] args){

//        Util.getConnection();
//        UserDaoJDBCImpl dao = new UserDaoJDBCImpl();
//        dao.createUsersTable();
//        dao.saveUser("Ivan", "Ivanov", (byte) 20);
//        dao.saveUser("Petr", "Petrov", (byte) 21);
//        dao.saveUser("Sidor", "Sidorov", (byte) 22);
//        dao.saveUser("Vasya", "Vasiliev", (byte) 23);
//        dao.getAllUsers();
//        dao.cleanUsersTable();
//        dao.dropUsersTable();
//        closeConnection();




        UserDaoHibernateImpl hibernateDao = new UserDaoHibernateImpl();
        hibernateDao.createUsersTable();
        hibernateDao.saveUser("Ivan", "Ivanov", (byte) 20);
        hibernateDao.saveUser("Petr", "Petrov", (byte) 21);
        hibernateDao.saveUser("Sidor", "Sidorov", (byte) 22);
        hibernateDao.saveUser("Vasya", "Vasiliev", (byte) 23);
        hibernateDao.getAllUsers();
        hibernateDao.cleanUsersTable();
        hibernateDao.dropUsersTable();
        Util.closeSessionFactory();
    }
}
