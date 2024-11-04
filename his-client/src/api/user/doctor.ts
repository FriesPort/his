import request from "../utils/request";


/**
 * 新增
 * */
export const addDocterApi = (params: any) => {
    return request.post('/system/docter/add', params)
}

/**
 * 编辑
 * */
export const editDocterApi = (params: any) => {
    return request.put('/system/docter/register', params)
}

//删除
export const deleteDocterApi = (params: any) => {
    return request.delete('/system/docter/delete',  {params:params} )
} 

//列表
export const getDocterListApi = (params: any) => {
    return request.get('/system/docter/query',{params:params})
}