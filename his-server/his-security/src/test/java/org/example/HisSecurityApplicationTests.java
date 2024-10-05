package org.example;

import com.example.dto.systemmanagement.userrole.UserRolePermissionSearchDTO;
import com.example.vo.JsonVO;
import com.example.vo.systemmanagement.userrole.UserRolePermissionSearchVO;
import org.example.client.SystemClient;
import org.example.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.List;

@SpringBootTest
class HisSecurityApplicationTests {

    @Autowired
    private JwtUtil jwtTool;
    @Autowired
    private SystemClient systemClient;
    @Test
    void contextLoads() {
        List<String> permissionList = List.of("user:add", "user:update");
        String token=jwtTool.createToken("1", Duration.ofMinutes(10),permissionList);
        System.out.println("token:"+token);
        String userId=jwtTool.parseTokenForUserId(token);
        System.out.println("userid"+userId);
        List<String> permissionList1=jwtTool.parseTokenForPermission(token);
        System.out.println("permissionList:"+permissionList1);
        UserRolePermissionSearchDTO dto=new UserRolePermissionSearchDTO("d0e3c968-c470-4fb3-af6f-2ba1cf18a8c2");
        JsonVO<List<UserRolePermissionSearchVO>> vo=systemClient.U_R_P_search(dto);
        System.out.println(vo);
    }

}
