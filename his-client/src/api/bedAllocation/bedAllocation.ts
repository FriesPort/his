import request from "@/utils/request.ts";

//提供调用床位数据的函数
export const bedAllocationRequest = (params: any) => {
  return request.put('/beds/assign', params)
}

//提供调用床位数据的函数
export const getBedRequest = (wardId: number) => {
  return request.get(`/beds/assign/${wardId}`)
}

//提供调用床位数据的函数
// export const getPatientRequest = (params: any) => {
//   return request.get('/patient/query', { params: params })
// }

//获取占用床位的数据
export const getOccupybedRequest =(params: any)=>{
  return request.get(`/assign/getOnBed`,params)
}

//获取空床位的数据
export const getEmptybedRequest =(params:any)=>{
  return request.get(`/assign/getOutBed`,params)
}

//处理患者出院|患者拒绝入院|患者未在规定时间办理入住
export const dischargebedRequest=(parames:any)=>{
  return request.post(`/assign/discharge`,parames)
}

//处理患者更改床位
export const changebedRequest=(parames:any)=>{
  return request.post(`/assign/changeBeds`,parames)
}

//床位预分配
export const preassignRequest=(parames:any)=>{
  return request.post(`/assign/preassign`,parames)
}

//患者入院
export const inHospitalRequest=(parames:any)=>{
  return request.post(`/assign/inHospital`,parames)
}

//患者出院
export const outHospitalRequest=(parames:any)=>{
  return request.post(`/assign/discharge`,parames)
}

// 患者通知
export const informRequest=(parames:any)=>{
  return request.get(`/assign/inform`,parames)
}