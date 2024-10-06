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