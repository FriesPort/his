import request from "@/utils/request.ts";

//提供调用登录接口的函数
export const userLoginService = (params: any) => {
    return request.post('/users/login', params )
}

//新增
export const addUserApi = (params: any) => {
    return request.post('/users/msg/add', params)
}


//编辑
export const editUserApi = (params: any) => {
    return request.put('/users/msg/update', params)
}

//删除
export const deleteUserApi = (params: any) => {
    return request.delete('/users/msg/delete',  {params:params} )
} 

//列表
export const getListApi = (params: any) => {
    return request.get('/users/msg/display',{params:params})
}

//获取角色列表
export const getRoleListApi = () => {
    return request.get('/roles/msg/display')
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
    return request.post('roles/permission/allocation',params)
}


//权限列表
export const permissionListApi = () =>{
    return request.get('/permissions/msg/display')
}