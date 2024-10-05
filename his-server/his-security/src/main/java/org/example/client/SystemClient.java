package org.example.client;

import com.example.dto.systemmanagement.userrole.UserRolePermissionSearchDTO;
import com.example.vo.JsonVO;
import com.example.vo.systemmanagement.userrole.UserRolePermissionSearchVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "his-system-management")
public interface SystemClient {
    @PostMapping("/users/roleandpermission/search")
    JsonVO<List<UserRolePermissionSearchVO>> U_R_P_search(@RequestBody UserRolePermissionSearchDTO userRolePermissionSearchDTO);

}
