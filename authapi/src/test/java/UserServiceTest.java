import com.example.models.Role;
import com.example.models.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.service.UserServiceImpl;
import com.example.service.base.RoleService;
import com.example.service.base.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    RoleService roleService;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    public void loadUserByUsername() {
        Mockito.when(userService.loadUserByUsername("username1"))
                .thenReturn(new User(1, "full name", "username1", "password1", "email1@email.com"));

        User user = userService.loadUserByUsername("username1");

        Assert.assertEquals("username1", user.getUsername());
        Assert.assertEquals("password1", user.getPassword());
    }

    @Test
    public void RegisterUser_ShouldCreateNewUser() throws Exception {

        User toCreateUser = new User();
        toCreateUser.setUserId(1);
        toCreateUser.setFullname("Aa");
        toCreateUser.setEmail("a@a.com");
        toCreateUser.setUsername("AAAAAA");
        toCreateUser.setPassword("111111");

        User newUser = new User();

        Optional<Role> role = Optional.of(new Role(1, "ROLE_USER"));

        Mockito.when(roleRepository.findById(1)).thenReturn(role);

        userService.register(newUser);

        Mockito.verify(roleRepository, Mockito.atLeast(1)).findById(1);
    }
}
