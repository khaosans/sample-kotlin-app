package dropwizard.kotlin.example.dao;

import dropwizard.kotlin.example.api.User;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.UUID;

@RegisterMapper(UserMapper.class)
public interface UserDao {

    @SqlUpdate("UPDATE users " +
            "SET  name=  :name, email = :email " +
            "WHERE id = :id;")
    void updateUser(@Bind("id") UUID id, @Bind("name") String name, @Bind("email") String email);

    @SqlUpdate("insert into users (id, name, email, password) values (:id, :name, :email, :password)")
    void insert(@Bind("id") UUID id, @Bind("name") String name, @Bind("email") String email, @Bind("password") String password);

    @SqlQuery("select * from users where id = :id")
    User findUserById(@Bind("id") UUID id);

    @SqlQuery("select * from users where email = :email limit 1")
    User findUserByEmail(@Bind("email") String email);

    @SqlQuery("select count(*) from users")
    int getCount();


    @SqlUpdate("DELETE FROM users WHERE id = :id")
    void delete(@Bind("id") Integer id);


    /**
     * close with no args is used to close the connection
     */
    void close();
}
