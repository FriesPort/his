package com.example.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.systemmanagement.campus.CampusUpdateDTO;
import com.example.entity.Campus;
import com.example.entity.Hospital;
import com.example.service.ICampusService;
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
 * @since 2024-11-05
 */
@RestController
@RequestMapping("/system/campus")
@Api(tags = "campus")
public class CampusController {
    @Autowired
    ICampusService campusService;

    /**
     * 院区信息查询
     * @param current
     * @param size
     * @return
     */
    @GetMapping("/list")
    public JsonVO<?> searchCampus(@RequestParam long current, @RequestParam long size){
        IPage<Campus> page= new Page<>(current,size);
        return JsonVO.success(campusService.list(page));
    }

    /**
     * 院区信息添加
     * @param campus
     * @param userId
     * @return
     */
    @PostMapping("/insert")
    public JsonVO<?> addCampus(@RequestBody Campus campus, @RequestHeader("userId") String userId){
        if(campusService.add(campus,userId)){
            return JsonVO.success(true);
        }else {
            return JsonVO.fail(false);
        }

    }

    /**
     *  院区信息删除
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public JsonVO<?> deleteCampus(@RequestParam String id){
        if(campusService.removeById(id)){
            return JsonVO.success(true);
        }else {
            return JsonVO.fail(false);
        }
    }

    /**
     * 院区信息修改
     * @param dto
     * @param userId
     * @return
     */
    @PostMapping("/update")
    public JsonVO<?> updateCampus(@RequestBody CampusUpdateDTO dto, @RequestHeader("userId") String userId){
        if(campusService.update(dto,userId)){
            return JsonVO.success(true);
        }else {
            return JsonVO.fail(false);
        }
    }
}

