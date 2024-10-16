import request from "@/utils/request2.ts";

//获取病人数据
export const getPatientsData = (params: any) => {
  return request.get('/patient/query', { params: params })
}

//患者删除请求
// export const patientDeleteRequest = (params: any) => {
//   return request.delete('/patient/delete',{params: params})
// }
export const patientDeleteRequest = (patientId: number) => {
  return request.delete(`/patient/delete/${patientId}`)
}
//患者编辑请求
export const patientEditRequest = (params: any) => {
  return request.put('/patient/edit', params )
}

//患者新增请求
export const patientAddRequest = (params: any) => {
  console.log('新增',params)
  return request.post('/patient/add',params)
}


//获取病区数据
// export const getWardsData = () => {
//   return request.get('/patients')
// }