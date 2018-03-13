import com.baozi.test.UserDao;
import com.baozi.test.UserDaoImp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by baozi on 2018/3/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-aspetj.xml")
public class UserDaoAspectJ {

//    @Autowired
//    UserDao userDao;

    @Test
    public void aspectJTest() {
        UserDao userDao = new UserDaoImp();
        userDao.addUser(10);
    }
}
