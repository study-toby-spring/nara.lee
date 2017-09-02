package springbook.user.dao;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.user.domain.User;

import java.sql.SQLException;

public class UserDaoConnectionCountingTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        UserDao dao = context.getBean("userDao", UserDao.class);

        // dao 사용 코드
        User user1 = new User("107", "이나라", "비밀번호");
        dao.add(user1);
        System.out.printf(user1.getId() + "등록 성공");

        User user2 = dao.get(user1.getId());
        System.out.println(user2.getName() + ", " + user2.getPassword() + " 조회 성공");

        User user3 = new User("108", "미나리", "비밀번호");
        dao.add(user3);
        System.out.printf(user3.getId() + "등록 성공");

        User user4 = dao.get(user3.getId());
        System.out.println(user4.getName() + ", " + user4.getPassword() + " 조회 성공");

        CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
        System.out.println("Connection counter: " + ccm.getCounter());
    }
}
