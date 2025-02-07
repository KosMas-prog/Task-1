/* В методе main класса Main должны происходить следующие операции:

1. Создание таблицы User(ов)
2. Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль (User с именем – name добавлен в базу данных)
3. Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
4. Очистка таблицы User(ов)
5. Удаление таблицы
*/



package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.util.List;


public class Main {
    // реализуйте алгоритм

    public static void main(String[] args){

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

