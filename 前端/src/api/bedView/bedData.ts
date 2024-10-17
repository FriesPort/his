import request from "@/utils/request.ts";

// 提供调用床位数据的函数
export const getBedsData = (params: any) => {
  return request.get('/beds/list', { params: params })
}

//提供调用床位数据的函数
export const getWardsData = () => {
  return request.get('/beds')
}

//提供删除床位的函数
export const bedDeleteRequest = (bedId: number) => {
  return request.delete(`/beds/delete/${bedId}`)
}

//释放床位请求
export const bedReleaseRequest = (params: any) => {
  return request.put('/beds/release', params)
}

//提供新增床位的函数
//获取相关病区病房
export const getRooms = (wardId: number) => {
  return request.get(`/beds/add/${wardId}`)
}
//新增床位请求
export const addBedRequest = (params: any) => {
  return request.post('/beds/add', params)
}