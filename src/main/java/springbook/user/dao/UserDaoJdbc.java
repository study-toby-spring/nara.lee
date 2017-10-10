package springbook.user.dao;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoJdbc implements UserDao {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<User> userMapper =
            new RowMapper<User>() {
                public User mapRow(ResultSet resultSet, int i) throws SQLException{
                    User user = new User();
                    user.setId(resultSet.getString("id"));
                    user.setName(resultSet.getString("name"));
                    user.setPassword(resultSet.getString("password"));
                    return user;
                }
            };

    public void add(final User user) throws DuplicateKeyException {
        this.jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)",
                user.getId(), user.getName(), user.getPassword());
    }

    public User get(String id){

        return this.jdbcTemplate.queryForObject("select * from users where id = ?", new Object[]{id}, this.userMapper);
    }

    public void deleteAll(){
        this.jdbcTemplate.update("delete from users");
    }


    public int getCount(){
//        return this.jdbcTemplate.query(new PreparedStatementCreator() {
//            public PreparedStatement createPreparedStatement(Connection connection){
//                return connection.prepareStatement("select count(*) from users");
//            }
//        }, new ResultSetExtractor<Integer>() {
//            public Integer extractData(ResultSet resultSet) throws SQLException, DataAccessException {
//                resultSet.next();
//                return resultSet.getInt(1);
//            }
//        });
        return this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query("select * from users order by id", this.userMapper);
    }


    /**
     * 왜 이 코드에 문제가 많다고 하는 것일까?
     * 잘 동작하는 코드를 굳이 수정하고 개선해야 하는 이유는 뭘까?
     * 그렇게 DAO 코드를 개선했을 때의 장점은 무엇인가?
     * 그런 장점들이 당장에, 또는 미래에 주는 유익은 무엇인가?
     * 또, 객체지향 설계의 원칙과는 무슨 상관이 있을까?
     * 이 DAO를 개선하는 경우와 그대로 사용하는 경우, 스프링을 사용하는 개발에서 무슨 차이가 있을까?
     */
}
