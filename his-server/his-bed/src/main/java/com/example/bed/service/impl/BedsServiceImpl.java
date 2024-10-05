package com.example.bed.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.bed.dto.BedAddDTO;
import com.example.bed.dto.BedDispatchDTO;
import com.example.bed.dto.BedSearchDTO;
import com.example.bed.entity.*;
import com.example.bed.service.*;
import com.example.bed.vo.Result;
import com.example.bed.mapper.BedsMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.bed.vo.bedAdd.RoomVo;
import com.example.bed.vo.bedAssign.BedVo;
import com.example.bed.vo.bedSearch.SearchVo;
import com.example.bed.vo.bedPage.CampusVo;
import com.example.bed.vo.bedPage.OfficeVo;
import com.example.bed.vo.bedPage.WardVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xw
 * @since 2024-04-26
 */
@Service
public class BedsServiceImpl extends ServiceImpl<BedsMapper, Bed> implements IBedsService {

    @Autowired
    private IRoomsService roomsService;
    @Autowired
    private IPatientService patientInformationService;
    @Autowired
    private ICampusService campusService;
    @Autowired
    private IOfficeService officeService;
    @Autowired
    private IWardService wardService;

    @Override   //页面信息提供
    public Result<List<CampusVo>> bedPage() {
        Result<List<CampusVo>> result = new Result<>();
        List<CampusVo> campusVo = new ArrayList<>();
        //获取院区信息
        List<Campus> campus = campusService.list();
        //拷贝院区信息
        for (Campus source : campus) {
            CampusVo target = new CampusVo();
            BeanUtils.copyProperties(source, target);
            campusVo.add(target);
        }


        for(CampusVo c : campusVo){
            //获取科室信息
            LambdaQueryWrapper<Office> officeLambdaQueryWrapper = new LambdaQueryWrapper<>();
            officeLambdaQueryWrapper.eq(Office::getCampusId,c.getCampusId());
            List<Office> offices = officeService.list(officeLambdaQueryWrapper);

            for(Office source : offices){
                OfficeVo target = new OfficeVo();
                BeanUtils.copyProperties(source,target);
                c.getOffices().add(target);
            }

            for (OfficeVo o : c.getOffices()){
                //获取病区信息
                LambdaQueryWrapper<Ward> wardLambdaQueryWrapper = new LambdaQueryWrapper<>();
                wardLambdaQueryWrapper.eq(Ward::getOfficeId,o.getOfficeId());
                List<Ward> wards = wardService.list(wardLambdaQueryWrapper);
                for(Ward source : wards){
                    WardVo target = new WardVo();
                    BeanUtils.copyProperties(source,target);
                    o.getWards().add(target);
                }
            }
        }

        result.setMessage(campusVo);
        result.setStatus(true);

        return result;
    }

    /**
     * 查询房间符合条件的床位
     * @param rooms 房间列表
     * @param bedSearchDTO 床位查看查询条件
     * @return 床位列表
     */
    @Override
    public List<Bed> bedList(List<com.example.bed.entity.Room> rooms, BedSearchDTO bedSearchDTO) {
        List<Bed> beds = new ArrayList<>();
        for (com.example.bed.entity.Room room : rooms){
            beds.addAll(lambdaQuery().eq(bedSearchDTO.getBedType()!=null, Bed::getBedType,bedSearchDTO.getBedType())
                            .eq(Bed::getRoomId,room.getRoomId())
                    .list());
        }
        return beds;
    }

    /**
     * 查询床位对应的患者信息
     * @param beds 床位列表
     * @return 床位及其对应的患者信息
     */
    @Override
    public List<SearchVo> bedPatientList(List<Bed> beds) {
        List<SearchVo> searchVos = new ArrayList<>();
        com.example.bed.entity.Room room;
        Patient patientInformation = null;

        SearchVo v ;
        for (Bed bed : beds){
            v = new SearchVo();
            //查询床位对应房间
            LambdaQueryWrapper<com.example.bed.entity.Room> roomsLambdaQueryWrapper = new LambdaQueryWrapper<>();
            roomsLambdaQueryWrapper.eq(com.example.bed.entity.Room::getRoomId,bed.getRoomId());
            room = roomsService.getOne(roomsLambdaQueryWrapper);

            //拷贝房间信息
            if(room != null){
                BeanUtils.copyProperties(room,v);
            }

            //查询床位对应患者并拷贝
            if(bed.getPatientId()!=null){
            LambdaQueryWrapper<Patient> infoLambdaQueryWrapper = new LambdaQueryWrapper<>();
            infoLambdaQueryWrapper.eq(Patient::getPatientId,bed.getPatientId());
            patientInformation = patientInformationService.getOne(infoLambdaQueryWrapper);
                BeanUtils.copyProperties(patientInformation,v);
                //处理入院时间类型
                v.setAdmissionTime(patientInformation.getAdmissionTime().format(Patient.DATE_FORMATTER));
            }

            BeanUtils.copyProperties(bed,v);
            //将拷贝完成的信息添加到列表
            searchVos.add(v);
        }
        return searchVos;
    }

    @Override   //查看病区空闲床位
    public Result<List<BedVo>> bedAssign(Integer wardId) {
        Result<List<BedVo>> result = new Result<>();
        LambdaQueryWrapper<com.example.bed.entity.Room> roomLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roomLambdaQueryWrapper.eq(com.example.bed.entity.Room::getWardId,wardId);
        //查询病区的房间
        List<com.example.bed.entity.Room> rooms = roomsService.list(roomLambdaQueryWrapper);
        ArrayList<BedVo> bedVos = new ArrayList<>();
        //遍历房间中的空闲床位
        for (com.example.bed.entity.Room room : rooms) {
            LambdaQueryWrapper<Bed> bedLambdaQueryWrapper = new LambdaQueryWrapper<>();
            bedLambdaQueryWrapper.eq(Bed::getRoomId,room.getRoomId())
                    .eq(Bed::getBedStatus,"空闲");
            List<Bed> beds = list(bedLambdaQueryWrapper);
            //拷贝床位所需信息
            for (Bed bed : beds) {
                BedVo bedVo = new BedVo();
                BeanUtils.copyProperties(bed,bedVo);
                BeanUtils.copyProperties(room,bedVo);
                bedVos.add(bedVo);
            }
        }
        result.setMessage(bedVos);
        result.setStatus(true);
        return result;
    }


    @Override   //床位分配
    public Result<String> bedAssign(BedDispatchDTO bedDispatchDTO) {
        Result<String> result = new Result<>();
        //获取指定床位信息
        Bed bed = this.getById(bedDispatchDTO.getBedId());
        //获取指定患者信息
        LambdaQueryWrapper<Patient> patientInformationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        patientInformationLambdaQueryWrapper.eq(Patient::getPatientId, bedDispatchDTO.getPatientId())
                .eq(Patient::getDel,0);
        Patient patientInformation = patientInformationService.getOne(patientInformationLambdaQueryWrapper);

        if (patientInformation == null) {
            result.setMessage("找不到指定患者患者");
            return result;
        }

        //判断患者状态
        if (!patientInformation.getDel().equals(0)){
            result.setMessage("患者已被删除");
            return result;
        }
        if (!patientInformation.getStatus().equals("待入院")) {
            result.setMessage("患者状态已变更，当前状态为"+patientInformation.getStatus());
            return result;
        }

        //检查床位是否空闲
        if(!bed.getBedStatus().equals("空闲")){
            result.setMessage("当前床位已被占用");
            return result;
        }

        //分配床位
        LambdaUpdateWrapper<Bed> bedsLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        bedsLambdaUpdateWrapper
                .set(Bed::getPatientId,patientInformation.getPatientId())
                .set(Bed::getBedStatus,"占用")
                .eq(Bed::getBedId,bed.getBedId());
        this.update(null,bedsLambdaUpdateWrapper);

        //获取床位的房间区域信息
        LambdaQueryWrapper<com.example.bed.entity.Room> roomLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roomLambdaQueryWrapper.eq(com.example.bed.entity.Room::getRoomId,bed.getRoomId());
        com.example.bed.entity.Room room = roomsService.getOne(roomLambdaQueryWrapper);

        //更新患者区域信息及修改患者状态
        LambdaUpdateWrapper<Patient> patientInformationLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        patientInformationLambdaUpdateWrapper
                .set(Patient::getWardId,room.getRoomId())
                .set(Patient::getOfficeId,room.getOfficeId())
                .set(Patient::getCampusId,room.getCampusId())
                .set(Patient::getStatus,"已入院")
                .set(Patient::getAdmissionTime,LocalDateTime.now())
                .set(Patient::getUpdateTime, LocalDateTime.now())
                .eq(Patient::getPatientId, bedDispatchDTO.getPatientId());
        //完成信息更新
        result.setStatus(patientInformationService.update(patientInformationLambdaUpdateWrapper));
        result.setMessage("分配成功");
        return result;
    }

    @Override   //患者出院，释放床位
    public Result<String> bedRelease(BedDispatchDTO bedDispatchDTO) {
        Result<String> result = new Result<>();
        Bed bed = getById(bedDispatchDTO.getBedId());
        Patient patient = patientInformationService.getById(bedDispatchDTO.getPatientId());
        //校验信息
        if (!Objects.equals(bed.getPatientId(), patient.getPatientId())){
            result.setMessage("患者和床位不匹配");
            return result;
        }
        //释放床位
        LambdaUpdateWrapper<Bed> bedLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        bedLambdaUpdateWrapper.set(Bed::getPatientId,null)
                        .set(Bed::getBedStatus,"空闲")
                        .eq(Bed::getBedId,bedDispatchDTO.getBedId());
        update(bedLambdaUpdateWrapper);
        //修改患者状态
        LambdaUpdateWrapper<Patient> patientLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        patientLambdaUpdateWrapper.set(Patient::getStatus,"已出院")
                .set(Patient::getDischargeTime,LocalDateTime.now())
                .eq(Patient::getPatientId,bedDispatchDTO.getPatientId());
        patientInformationService.update(patientLambdaUpdateWrapper);

        result.setMessage("完成出院");
        result.setStatus(true);
        return result;
    }

    @Override
    public Result<List<RoomVo>> bedAdd(Integer wardId) {
        Result<List<RoomVo>> result = new Result<>();
        LambdaQueryWrapper<Room> roomLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roomLambdaQueryWrapper.eq(Room::getWardId,wardId);
        List<Room> rooms = roomsService.list(roomLambdaQueryWrapper);
        ArrayList<RoomVo> roomVos = new ArrayList<>();
        for (Room room : rooms) {
            RoomVo roomVo = new RoomVo();
            BeanUtils.copyProperties(room,roomVo);
            roomVos.add(roomVo);
        }
        result.setMessage(roomVos);
        result.setStatus(true);
        return result;
    }
    /**
     * 添加床位
     * @param bedAddDTO 新增床位信息
     * @return 执行结果
     */
    @Override
    public Result<String> bedAdd(BedAddDTO bedAddDTO) {
        Result<String> result = new Result<>();
        //获取新增床位的房间信息
        com.example.bed.entity.Room room = roomsService.getById(bedAddDTO.getRoomId());
        //检查房间
        if (room == null) {
            result.setMessage("找不到指定房间");
            return result;}
        //检查床位
        if (!lambdaQuery()
                .eq(Bed::getRoomId, room.getRoomId())
                .eq(Bed::getBedNumber, bedAddDTO.getBedNumber())
                .list().isEmpty()) {
            result.setMessage("该房间已存在相同编号的床位");
            return result;
        }
        //检查房间性别限制
        if (!room.getRoomGender().equals("混合病房")&&
              !((room.getRoomGender().equals("男性病房")&&bedAddDTO.getBedType().equals("男床"))
               ||room.getRoomGender().equals("女性病房")&&bedAddDTO.getBedType().equals("女床"))){
            result.setMessage("该病房不支持添加"+bedAddDTO.getBedType());
            return result;
        }
        //生成指定条件的床位
        Bed bed = new Bed();
        BeanUtils.copyProperties(bedAddDTO,bed);
        //新增床位
        this.save(bed);
        //房间床位数量增加
        LambdaUpdateWrapper<com.example.bed.entity.Room> roomsLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        roomsLambdaUpdateWrapper.set(com.example.bed.entity.Room::getBedCount,room.getBedCount()+1)
                .eq(com.example.bed.entity.Room::getRoomId,bedAddDTO.getRoomId());
        roomsService.update(roomsLambdaUpdateWrapper);
        result.setStatus(true);
        result.setMessage("添加成功");
        return result;
    }

    @Override
    public Result<String> bedDelete(Long bedId) {
        Result<String> result = new Result<>();
        //获取床位指定信息
        Bed bed = this.getById(bedId);
        if (bed==null){
            result.setMessage("找不到该床位");
            return result;
        }
        //判断床位使用情况
        if (bed.getBedStatus().equals("空闲")) {
            //获取床位的房间信息
            com.example.bed.entity.Room room = roomsService.getById(bed.getRoomId());
            //床位数量-1
            room.setBedCount(room.getBedCount()-1);
            //更新数据库
            LambdaUpdateWrapper<com.example.bed.entity.Room> roomLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            roomLambdaUpdateWrapper.eq(com.example.bed.entity.Room::getRoomId,room.getRoomId());
            roomsService.update(room,roomLambdaUpdateWrapper);
            removeById(bedId);
            result.setStatus(true);
            result.setMessage("删除成功");
            return result;
        }
        if(bed.getBedStatus().equals("占用")){
            result.setMessage("当前床位存在患者");
            return result;
        }
        result.setMessage("当前床位非空闲");
        return result;
    }
}