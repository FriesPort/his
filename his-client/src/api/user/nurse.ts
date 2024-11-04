import request from "../utils/request";


/**
 * 新增
 * */
export const addNurseApi = (params: any) => {
    return request.post('/system/nurse/add', params)
}

/**
 * 编辑
 * */
export const editNurseApi = (params: any) => {
    return request.put('/system/nurse/register', params)
}

//删除
export const deleteNurseApi = (params: any) => {
    return request.delete('/system/nurse/delete',  {params:params} )
} 

//列表
export const getNurseListApi = (params: any) => {
    return request.get('/system/nurse/query',{params:params})
}