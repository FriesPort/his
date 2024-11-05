import request from "../utils/request";

//用户-移除用户的角色
export const deleteUserRoleApi = (userId: number,roleId: number) => {
    return request.get(`/system/user/role/delete?userId = ${userId}&roleId = ${roleId}`)
}

//用户-获取未拥有的角色列表
export const getOwnedRoleApi = (userId: number) => {
    return request.get(`/system/user/role/getOwnedRole?userId = ${userId}`)
}

//用户-获取未拥有的角色列表
export const getUnownedRoleApi = (userId: number) => {
    return request.get(`/system/user/role/getUnownedRole?userId = ${userId}`)
}

//获取角色列表
export const getRoleListApi = (params:any) => {
    return request.get('/system/role/search',{params:params})
}

//获取指定用户已有的角色
export const getUserRole = (params: any) => {
    return request.get('/users/roleandpermission/search',{params:params} )
}

//分配用户权限
export const allocationPermission = (params: any) => {
    return request.put('/users/role/update', params)
}

//删除用户角色
export const deletePermission = (params: any) => {
    // 将对象转换为查询字符串
    const queryString = Object.keys(params)
        .map(key => `${encodeURIComponent(key)}=${encodeURIComponent(params[key])}`)
        .join('&');
    // 拼接完整的URL
    const url = `/users/role/delete?${queryString}`;
    return request.delete(url);
}

//自定义用户
export const userCustomize = (params:any) => {
    return request.post('/users/role/allocation',params)
}

//自定义角色
export const roleAdd = (params:any) => {
    return request.post('/system/role/register',params)
}

//编辑角色
export const roleEditApi = (params: any) => {
    return request.post('/system/role/allocation', params)
}

//删除用户角色
export const roleDeleteApi = (params:any) => {
    return request.post(`/system/role/delete?id=${params}`);
}

//权限列表
export const permissionListApi = () =>{
    return request.get('/permissions/msg/display')
}