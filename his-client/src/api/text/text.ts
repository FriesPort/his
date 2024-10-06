import request from "@/utils/request0.ts";

//测试
export const getText = () => {
  return request.get('/test')
}
