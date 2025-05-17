//package mealmover.backend;
//
//import mealmover.backend.dtos.requests.RoleCreateRequestDto;
//import mealmover.backend.dtos.responses.RoleResponseDto;
//import mealmover.backend.enums.Role;
//import mealmover.backend.models.RoleModel;
//import mealmover.backend.repositories.RoleRepository;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.junit.jupiter.api.Assertions;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.UUID;
//
//@SpringBootTest
//public class RoleServiceTest {
//    @InjectMocks
//    private RoleService roleService;
//    @Mock
//    private RoleMapper roleMapper;
//    @Mock
//    private RoleRepository roleRepository;
//
//    @Test
//    void createRole_ShouldSaveRoleAndReturnSavedRole() {
//        UUID uuid = UUID.randomUUID();
//
//        String roleName = Role.ADMIN.toConvert();
//
//        RoleModel mockedRoleWithId = new RoleModel();
//        mockedRoleWithId.setId(uuid);
//        mockedRoleWithId.setName(roleName);
//
//        RoleModel mockedRoleWithoutId = new RoleModel();
//        mockedRoleWithoutId.setName(roleName);
//
//        RoleCreateRequestDto mockedRequestDto = new RoleCreateRequestDto();
//        mockedRequestDto.setName(roleName);
//
//        RoleResponseDto mockedResponseDto = new RoleResponseDto();
//        mockedResponseDto.setId(uuid);
//        mockedResponseDto.setName(roleName);
//
//        Mockito.when(roleMapper.toDto(mockedRoleWithId)).thenReturn(mockedResponseDto);
//        Mockito.when(roleMapper.toModel(mockedRequestDto)).thenReturn(mockedRoleWithoutId);
//
//        Mockito.when(roleRepository.save(Mockito.any(RoleModel.class))).thenReturn(mockedRoleWithId);
//        Mockito.when(roleRepository.findByName(roleName)).thenReturn(java.util.Optional.empty());
//
//        RoleResponseDto responseDto = roleService.create(mockedRequestDto);
//
//        Assertions.assertNotNull(responseDto);
//        Assertions.assertEquals(uuid, responseDto.getId());
//        Assertions.assertEquals(roleName, responseDto.getName());
//        Mockito.verify(roleRepository, Mockito.times(1)).save(Mockito.any(RoleModel.class));
//    }
//}