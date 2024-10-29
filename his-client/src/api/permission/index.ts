import request from "@/utils/request.ts";

// 提供调用床位数据的函数
export const getPermissionList = (params: any) => {
  return request.get('/system/permission/search', { params: params })
}

export const permisionRegister = (params: any) => {
  return request.post('/system/permission/register', params)
}

export const permisionEdit = (params: any) => {
  return request.post('/system/permission/update', params)
}

export const permisionRuleEdit = (params: any) => {
  return request.post('/system/permission/datarule/update', params)
}

//删除
export const permissionDelete = (params: any) => {
  return request.post(`/system/permission/delete?id=${params.id}`, params)
} 