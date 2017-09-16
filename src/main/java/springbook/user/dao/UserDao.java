package springbook.user.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.*;

/**
 * 1. DB 연결을 위한 Connection을 가져온다.
 * 2. SQL을 담은 Statement(PreparedStatement)를 만든다.
 * 3. 만들어진 Statement를 실행한다.
 * 4. 조회의 경우 SQL 쿼리의 실행 결과를 ResultSet으로 받아서 정보를 저장한다.
 * 5. 작업 중에 생성된 리소스는 작업을 마친 후 반드시 닫아준다.
 */
public class UserDao {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void add(User user) throws SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("select * from users where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();

        User user = null;
        if(rs.next()){
            user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }

        rs.close();
        ps.close();
        c.close();

        if(user == null) throw new EmptyResultDataAccessException(1);

        return user;
    }

    public void deleteAll() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;

        try {
            c = dataSource.getConnection();

            StatementStrategy strategy = new DeleteAllStatement();
            ps = strategy.makePreparedStatement(c);

            ps.executeUpdate();
        }catch (SQLException e){
            throw e;
        }finally {
            if(ps != null){
                try{
                    ps.close();
                }catch (SQLException e){ }
            }

            if(c != null){
                try{
                    c.close();
                }catch (SQLException e){ }
            }
        }

        ps.close();
        c.close();
    }

    public int getCount() throws SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            c = dataSource.getConnection();
            ps = c.prepareStatement("select count(*) from users");
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        }catch (SQLException e){
            throw e;
        }finally {
            if(rs != null){
                try{
                    rs.close();
                }catch (SQLException e){ }

                try{
                    ps.close();
                }catch (SQLException e){ }

                try{
                    c.close();
                }catch (SQLException e){ }
            }
        }
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
