package springbook.user.dao;

import com.mysql.jdbc.MysqlErrorNumbers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import springbook.Exception.DuplicateUserIdException;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.util.List;

/**
 * 1. DB 연결을 위한 Connection을 가져온다.
 * 2. SQL을 담은 Statement(PreparedStatement)를 만든다.
 * 3. 만들어진 Statement를 실행한다.
 * 4. 조회의 경우 SQL 쿼리의 실행 결과를 ResultSet으로 받아서 정보를 저장한다.
 * 5. 작업 중에 생성된 리소스는 작업을 마친 후 반드시 닫아준다.
 */
public class UserDao {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<User> userMapper =
            new RowMapper<User>() {
                public User mapRow(ResultSet resultSet, int i) throws SQLException {
                    User user = new User();
                    user.setId(resultSet.getString("id"));
                    user.setName(resultSet.getString("name"));
                    user.setPassword(resultSet.getString("password"));
                    return user;
                }
            };

    public void setJdbcTemplate(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(final User user) throws DuplicateUserIdException {
        try {
            this.jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)",
                    user.getId(), user.getName(), user.getPassword());
        }catch(SQLException e){
            if(e.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY) {
                throw new DuplicateUserIdException(e);
            }else{
                throw new RuntimeException(e);
            }
        }
    }

    public User get(String id) throws SQLException {

        return this.jdbcTemplate.queryForObject("select * from users where id = ?", new Object[]{id}, this.userMapper);
    }

    public void deleteAll() throws SQLException {
        this.jdbcTemplate.update("delete from users");
    }


    public int getCount() throws SQLException {
//        return this.jdbcTemplate.query(new PreparedStatementCreator() {
//            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
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
