package springbook.user.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import springbook.user.domain.User;

import java.sql.SQLException;

public class UserDaoTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ApplicationContext context = new GenericXmlApplicationContext("context/application-context.xml");

        UserDao dao  = context.getBean("userDao", UserDao.class); //@Bean

        User user = new User();
        user.setId("116");
        user.setName("이나라");
        user.setPassword("비밀번호");

        dao.add(user);
        User user2 = dao.get(user.getId());
        if(!user.getName().equals(user2.getName())){
            System.out.println("테스트 실패 (name)");
        }else if(!user.getPassword().equals(user2.getPassword())){
            System.out.println("테스트 실패 (password)");
        }else{
            System.out.printf("조회 테스트 성공");
        }
    }
}
