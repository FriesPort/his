import request from "../utils/request";


/**
 * 新增
 * */
export const addNurseApi = (params: any) => {
    return request.post('/system/user/add', params)
}

/**
 * 编辑
 * */
export const editNurseApi = (params: any) => {
    return request.post('/system/user/register', params)
}

//删除
export const deleteNurseApi = (params: any) => {
    return request.post('/system/user/delete',  {params:params} )
} 

//列表
export const getNurseListApi = (params: any) => {
    return request.get('/system/user/query',{params:params})
}