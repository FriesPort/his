import request from "@/utils/request.ts";

//查找患者信息
export const getPatientsData = (params: any) => {
  return request.post('/patient/query', params)
}

//患者删除请求
export const patientDeleteRequest = (params: any) => {
  return request.post(`/patient/delete`, params)
}

//患者编辑请求
export const patientEditRequest = (params: any) => {
  return request.post('/patient/edit', params )
}

//患者新增请求
export const patientAddRequest = (params: any) => {
  console.log('新增',params)
  return request.post('/patient/add',params)
}
