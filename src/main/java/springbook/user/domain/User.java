package springbook.user.domain;

/**
 * 자바빈 규약을 따르는 오브젝트 생성
 *
 * 자바빈은 원래 비주얼 툴에서 조작 가능한 컴포넌트를 말한다.
 * 지금은 자바빈이라고 말하면 비주얼 컴포넌트라기보다는 다음 두 가지 관례에 따라 만들어진 오브젝트를 가리킨다.
 *
 * 1. 디폴트 생성자
 * 2. 프로퍼티 (getter, setter)
 */
public class User {
    String id;
    String name;
    String password;

    Level level;
    int login;
    int recommend;

    public User() {
    }

    public User(String id, String name, String password, Level level, int login, int recommend) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.level = level;
        this.login = login;
        this.recommend = recommend;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Level getLevel() {
        return level;
    }

    public int getLogin() {
        return login;
    }

    public int getRecommend() {
        return recommend;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public void setRecommend(int recommend) {
        this.recommend = recommend;
    }
}
