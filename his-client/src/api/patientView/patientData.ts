import request from "@/utils/request2.ts";

//获取病人数据
export const getPatientsData = (params: any) => {
  return request.get('/Patient/findPatient', { params: params })
}

//患者删除请求
export const patientDeleteRequest = (patientId: number) => {
  return request.delete(`/Patient/deletePatient${patientId}`)
}
//患者编辑请求
export const patientEditRequest = (params: any) => {
  return request.post('/Patient/revisePatient', params )
}

//患者新增请求
export const patientAddRequest = (params: any) => {
  console.log('新增',params)
  return request.post('/Patient/addPatient',params)
}
