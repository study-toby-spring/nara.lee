package springbook.user.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.user.domain.User;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class); // @Configuration
        UserDao dao  = context.getBean("userDao", UserDao.class); //@Bean

        User user = new User();
        user.setId("104");
        user.setName("이나라");
        user.setPassword("비밀번호");

        dao.add(user);
        System.out.printf(user.getId() + "등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName() + ", " + user2.getPassword() + " 조회 성공");

        //////////////////////////
        // identity vs equality
        //////////////////////////

        DaoFactory factory = new DaoFactory();
        UserDao dao1 = factory.userDao();
        UserDao dao2 = factory.userDao();

        System.out.println(dao1);
        System.out.println(dao2);

        UserDao dao3  = context.getBean("userDao", UserDao.class); //@Bean
        UserDao dao4  = context.getBean("userDao", UserDao.class); //@Bean

        System.out.println(dao3);
        System.out.println(dao4);
    }
}
