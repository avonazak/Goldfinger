import com.example.AuthApiApplication;
import com.example.models.Role;
import com.example.repository.RoleRepository;
import com.example.service.RoleServiceImpl;
import com.example.service.base.RoleService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class RoleServiceTest {

    @Mock
    RoleRepository roleRepository;

    @InjectMocks
    RoleServiceImpl roleService;

    @Test
    public void findById_User() {
        Mockito.when(roleRepository.findById(1)).thenReturn(Optional.of(new Role(1, "ROLE_USER")));

        Role role = roleService.findById(1);

        Assert.assertEquals(1, role.getRoleId());
        Assert.assertEquals("ROLE_USER", role.getRole());
    }

    @Test
    public void findById_Admin() {
        Mockito.when(roleRepository.findById(2)).thenReturn(Optional.of(new Role(2,"ROLE_ADMIN")));

        Role role = roleService.findById(2);

        Assert.assertEquals(2, role.getRoleId());
        Assert.assertEquals("ROLE_ADMIN", role.getRole());
    }
}
