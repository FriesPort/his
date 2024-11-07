package com.example.controller;


import com.example.dto.systemmanagement.dict.HisDictItemAddDTO;
import com.example.dto.systemmanagement.dict.HisDictItemEditDTO;
import com.example.entity.HisDictItem;
import com.example.service.IHisDictItemService;
import com.example.service.IHisDictService;
import com.example.vo.JsonVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author diandian
 * @since 2024-11-07
 */
@RestController
@RequestMapping("/system/dict")
@Api("字典管理")
public class HisDictController {
    @Autowired
    private IHisDictService hisDictService;
    @Autowired
    private IHisDictItemService hisDictItemService;

    /**
     * 根据字典id查询字典子项
     * @param dictId
     * @return
     */
    @GetMapping("/listByDictId")
    public JsonVO<?> listByDictId(String dictId){
        if(dictId.isEmpty()){
            return JsonVO.success(hisDictItemService.list());
        }else {
            return JsonVO.success(hisDictItemService.lambdaQuery().eq(HisDictItem::getDictId,dictId).list());

        }
    }

    /**
     * 根据id删除字典子项
     * @param id
     * @return
     */
    @PostMapping("/deleteById")
    public JsonVO<?> deleteById(String id){
        if(hisDictItemService.removeById(id)){
            return JsonVO.success("删除成功");
        }
        return JsonVO.fail("删除失败");
    }

    /**
     * 新增字典子项
     * @param dto
     * @param userId
     * @return
     */
    @PostMapping("/add")
    public JsonVO<?> add(@RequestBody HisDictItemAddDTO dto,@RequestHeader("userId")String userId){
        if(hisDictItemService.addDictItem(dto,userId)){
            return JsonVO.success("添加成功");
        }
        return JsonVO.fail("添加失败");
    }

    /**
     * 修改字典子项
     * @param dto
     * @param userId
     * @return
     */
    @PostMapping("/editById")
    public JsonVO<?> editById(@RequestBody HisDictItemEditDTO dto, @RequestHeader("userId")String userId){
        if(hisDictItemService.editDictItem(dto,userId)){
            return JsonVO.success("修改成功");
        }else{
            return JsonVO.fail("修改失败");
        }
    }
}

