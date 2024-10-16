export interface UserType {
    userId: string,
    name: string,
    username: string,
    password: string,
    email: string,
    phone: string,
    type: string,
    roleId: string,
    sex: string
}
//列表参数
export interface ListParm {
    currentPage: number,
    pageSize: number,
    phone: string,
    name: string
}