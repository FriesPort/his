package com.example.bed.Service.Impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.example.bed.mapper.*;
import com.example.bed.entity.*;
import com.example.bed.Service.BedsService;
import com.example.dto.bed.BedAddDTO;
import com.example.dto.bed.BedSearchDTO;
import com.example.dto.bed.BedUpdataDTO;
import com.example.utils.IdGenerate;
import com.example.vo.bed.BedVo;
import com.example.vo.bed.RoomListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.beans.BeanUtils.copyProperties;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)//事务回滚
public class BedsServiceImpl extends ServiceImpl<BedsMapper,Bed> implements BedsService {
    @Autowired
    private BedsMapper bedsMapper;
    @Autowired
    private RoomsMapper roomsMapper;
    @Autowired
    private PatientInformationMapper patientInformationMapper;
    @Autowired
    private CampusMapper campusMapper;
    @Autowired
    private OfficeMapper officeMapper;
    @Autowired
    private WardMapper wardMapper;
    @Autowired
    private FloorMapper floorMapper;
    @Autowired
    private BedlogsMapper bedlogMapper;



    //单个添加床位
    @Override
    public Result<String> insertBed(String userId,BedAddDTO bedAddDTO) {
        Result<String> result= new Result<>();
        //将BedDTO对象转换成Bed实体对象
        Bed bed = new Bed();
        copyProperties(bedAddDTO, bed);
        //生成床位编号number
        //1.将生成number需要的信息放到Number中储存
//        Numbers numbers = new Numbers();
        //从Room中获取信息
        Room room = roomsMapper.selectById(bedAddDTO.getRoomId());
//        //将外键分割
//        String[] foreignId = room.getForeignId().split("-");
        //分别将对应的信息放入
//        Campus campus= campusMapper.selectById(foreignId[2]);
//        numbers.setCampus(campus.getCampusName());
//        Office office = officeMapper.selectById(foreignId[1]);
//        numbers.setOffice(office.getOfficeName());
//        Ward ward = wardMapper.selectById(foreignId[0]);
//        numbers.setWard(ward.getWardName());
//        Floor floor = floorMapper.selectById(foreignId[3]);
//        numbers.setFloor(floor.getFloorName());
//        numbers.setRoom(room.getNumber());
//        numbers.setBednumber(bedAddDTO.getBednumber());
        //2.调用NumberGenerate生成number
//        bed.setNumber(NumberGenerate.nextNumbers(numbers));
        //查询添加的床位的number是否重复
//        if (Db.lambdaQuery(Bed.class).eq(Bed::getNumber, bed.getNumber()).exists()) {
//            result.setMessage("床位已存在");
//            return result;
//        }
        //判断room是否存在
        if (!Db.lambdaQuery(Room.class).eq(Room::getId, bed.getRoomId()).exists()) {
            result.setMessage("房间不存在！");
            return result;
        }
        //根据number查重
        //查询roomId中的bed类里面的number是否重复
        if(Db.lambdaQuery(Bed.class).eq(Bed::getRoomId, bed.getRoomId()).exists()){
            //创建一个容器存储这个room的bed对象
            List<Bed> bedList = lambdaQuery().eq(Bed::getRoomId, bed.getRoomId()).list();
            for (Bed bed1 : bedList) {
                //判断number是否重复
                if (bed1.getNumber().equals(bed.getNumber())){
                    result.setMessage("床位已存在！");
                    return result;
                }
            }
        }
        //插入床位信息
        bed.setCreateBy(userId);
        bed.setCreateTime(LocalDateTime.now());
        IdGenerate idGenerate=new IdGenerate();
        String uuid=idGenerate.nextUUID(bed);
        bed.setBedId(uuid);
        //判断床位是否有病人
        if (bed.getPatientInformationId()!=null) {
            //床位存在病人，直接填写病床使用日志
            Bedlog bedlog = new Bedlog();
            bedlog.setBedId(uuid);
            bedlog.setPatientInfomationId(bed.getPatientInformationId());
            bedlog.setUsingFlag(1);//添加进来的床位有病人关联说明床位是被占用的
            bedlog.setCreateTime(LocalDateTime.now());
            bedlog.setCreateBy(userId);
            bedlog.setId(idGenerate.nextUUID(bedlog));
            bedlogMapper.insert(bedlog);
        }else{
            bed.setPatientInformationId("null");
        }
        bedsMapper.insert(bed);
        //修改关联的房间信息
        room.setBedCount(room.getBedCount()+1);
        //修改人信息---
        room.setUpdateTime(LocalDateTime.now());
        room.setUpdateBy(userId);
        roomsMapper.updateById(room);
        result.setStatus(true);
        result.setMessage("添加成功");
        return result;
    }

    //修改床位信息
    @Override
    public Result<String> updateBed(String userId,BedUpdataDTO bedUpdataDTO) {
        Result<String> result= new Result<>();
        Bed bed = new Bed();
        BeanUtils.copyProperties(bedUpdataDTO, bed);
        //查询床位的id是否存在
        if (!(Db.lambdaQuery(Bed.class).eq(Bed::getBedId, bed.getBedId()).exists())) {
            result.setMessage("床位不存在");
            return result;
        }
        //修改床位信息
        lambdaUpdate().eq(Bed::getBedId, bed.getBedId())
                .set(bed.getNumber()!=null,Bed::getNumber, bed.getNumber())
                .set(bed.getBedType()!=null,Bed::getBedType, bed.getBedType())
                .set(bed.getBedStatus()!=null,Bed::getBedStatus, bed.getBedStatus())
                .set(bed.getRoomId()!=null,Bed::getRoomId, bed.getRoomId())
                .set(bed.getPatientInformationId()!=null,Bed::getPatientInformationId, bed.getPatientInformationId())
                .set(Bed::getUpdateBy, userId)
                .set(Bed::getUpdateTime, LocalDateTime.now())
                .update();
        result.setStatus(true);
        result.setMessage("修改成功");
        return result;
    }


    /**
     * 床位列表查询
     * @param bedSearchDTO
     * 传入的数据为：
     * campusId    officeId    wardId    bedType   bedStatus   roomType    roomGender
     *    院区         科室       病区     床位类型     床位状态      病房类型      病房性别
     * @return
     */

    //床位列表查询
    @Override
    public Result<List<BedVo>> bedList(BedSearchDTO bedSearchDTO) {
        Result<List<BedVo>> result= new Result<>();
        //拿到数据库中所有的room信息
        List<Room> roomList = roomsMapper.selectList(null);
        //通过遍历再次筛选Room中符合条件的Bed
        //1、遍历roomList将foreignId进行数据处理
        List<Room> roomList1= new ArrayList<>();
        for (Room room:roomList) {
            //通过split方法将foreignId进行拆分
            String[] split = room.getForeignId().split("-");
            //查询切分后的数据是否符合
            //2、判断roomList1是否为空
          if((StrUtil.isEmpty(bedSearchDTO.getCampusId()) || bedSearchDTO.getCampusId().equals(split[0]))){
              if((StrUtil.isEmpty(bedSearchDTO.getOfficeId())) || bedSearchDTO.getOfficeId().equals(split[1])){
                  if((StrUtil.isEmpty(bedSearchDTO.getWardId()))|| bedSearchDTO.getWardId().equals(split[2])){
                      if((StrUtil.isEmpty(bedSearchDTO.getRoomType()) || bedSearchDTO.getRoomType().equals(room.getType()))){
                          if((StrUtil.isEmpty(bedSearchDTO.getRoomGender()) || bedSearchDTO.getRoomGender().equals(room.getGender()))){
                              //将正确的数据存储到集合中
                              roomList1.add(room);
                          }
                      }
                  }
              }
          }
        }
        //获取通过条件筛选出来符合条件的Room的集合
        //通过遍历再次筛选Room中符合条件的Bed
        List<BedVo> bedVoList = new ArrayList<>();
        for (Room room : roomList1) {
            //根据roomid查询bed
            List<Bed> bedList = lambdaQuery().eq(Bed::getRoomId, room.getId())
                    .eq(!StrUtil.isEmpty(bedSearchDTO.getBedType()),Bed::getBedType, bedSearchDTO.getBedType())
                    .eq(bedSearchDTO.getBedStatus()!=null,Bed::getBedStatus, bedSearchDTO.getBedStatus())
                    .list();
            //将符合条件的bed添加到bedVoList中
            for (Bed bed : bedList) {
                BedVo bedVo = new BedVo();
                //将bed中的数据复制到bedVo中
                bedVo.setBedId(bed.getBedId());
                bedVo.setBedNumber(bed.getNumber());
                bedVo.setBedType(bed.getBedType());
                bedVo.setBedStatus(bed.getBedStatus());
                //从RoomId中查询此病床的病房数据
                Room room1 = roomsMapper.selectById(bed.getRoomId());
                bedVo.setRoomType(room1.getType());
                bedVo.setRoomGender(room1.getGender());
//                //判断该病床是否有关联病人
//                if (!(bed.getPatientInformationId().equals("null"))){
//                    //有关联病人，查询病人信息
//                    PatientInformation patientInformation = patientInformationMapper.selectById(bed.getPatientInformationId());
//                    bedVo.setPatientName(patientInformation.getPatientName());
//                }
                bedVoList.add(bedVo);
            }
        }
        //返回数据
        result.setStatus(true);
        result.setMessage(bedVoList);
        return result;
    }


    //批量添加床位
    @Override
    public Result<String> insertBatchBed(String userId,ArrayList<BedAddDTO> bedAddDTOList) {
        Result<String> result= new Result<>();
        long count = 0;//用来记录成功导入的条数
        //循环添加Bed信息
        IdGenerate idGenerate= new IdGenerate();
        for (BedAddDTO bedAddDTO : bedAddDTOList) {
            //先组装number
            Bed bed = new Bed();
            copyProperties(bedAddDTO, bed);
            //生成床位编号number
            //1.将生成number需要的信息放到Number中储存
//            Numbers numbers = new Numbers();
            //从Room中获取信息
            Room room = roomsMapper.selectById(bedAddDTO.getRoomId());
            //将外键分割
//            String[] foreignId = room.getForeignId().split("-");
//            //分别将对应的信息放入
//            Campus campus= campusMapper.selectById(foreignId[0]);
//            numbers.setCampus(campus.getCampusName());
//            Office office = officeMapper.selectById(foreignId[1]);
//            numbers.setOffice(office.getOfficeName());
//            Ward ward = wardMapper.selectById(foreignId[2]);
//            numbers.setWard(ward.getWardName());
//            Floor floor = floorMapper.selectById(foreignId[3]);
//            numbers.setFloor(floor.getFloorName());
//            numbers.setRoom(room.getNumber());
//            numbers.setBednumber(bedAddDTO.getBednumber());
//            //2.调用NumberGenerate生成number
//            bed.setNumber(NumberGenerate.nextNumbers(numbers));
//            //判断床位是否重复
//            if (Db.lambdaQuery(Bed.class).eq(Bed::getNumber, bed.getNumber()).exists()) {
//                continue;
//            }
            //判断room是否存在
            if (!Db.lambdaQuery(Room.class).eq(Room::getId, bed.getRoomId()).exists()) {
                continue;
            }
            //根据number查重
            //查询roomId中的bed类里面的number是否重复
            if(Db.lambdaQuery(Bed.class).eq(Bed::getRoomId, bed.getRoomId()).exists()){
                int flag=0;
                //创建一个容器存储这个room的bed对象
                List<Bed> bedList = lambdaQuery().eq(Bed::getRoomId, bed.getRoomId()).list();
                for (Bed bed1 : bedList) {
                    //判断number是否重复
                    if (bed1.getNumber().equals(bed.getNumber())){
                        flag=1;
                        break;
                    }
                }
                if(flag==1){
                    continue;
                }
            }

            //添加的床位信息不重复，继续执行操作
            //添加床位信息
            bed.setCreateBy(userId);
            bed.setCreateTime(LocalDateTime.now());
            String uuid=idGenerate.nextUUID(bed);
            bed.setBedId(uuid);

            //判断床位是否有病人
            if (bed.getPatientInformationId()!=null) {
                //床位存在病人，直接填写病床使用日志
                Bedlog bedlog = new Bedlog();
                bedlog.setBedId(uuid);
                bedlog.setPatientInfomationId(bed.getPatientInformationId());
                bedlog.setUsingFlag(1);//添加进来的床位有病人关联说明床位是被占用的
                bedlog.setCreateTime(LocalDateTime.now());
                bedlog.setCreateBy(userId);
                bedlog.setId(idGenerate.nextUUID(bedlog));
                bedlogMapper.insert(bedlog);
            }else{
                bed.setPatientInformationId("null");
            }
            bedsMapper.insert(bed);
            count++;
            //修改关联的房间信息
            room.setBedCount(room.getBedCount()+1);
            //修改人信息---
            room.setUpdateTime(LocalDateTime.now());
            roomsMapper.updateById(room);
        }
        if(count==0){
            result.setMessage("导入失败，请检查数据格式");
            return result;
        }
        result.setStatus(true);
        result.setMessage("成功导入"+count+"条数据");
        return result;
    }


//    @Override
//    public Result<String> insertBatchBed(MultipartFile excelFile) {
//        Result<String> result= new Result<>();
//        //将excel文件转换成List<Numbers>对象
//        //获取excel文件的url
//        String url = excelFile.getClass().getResource("").getPath();
//        ExcelReader excelReader = ExcelUtil.getReader(url);
//        List<Numbers> numbersList = excelReader.readAll(Numbers.class);
//        //判断集合是否为空
//        if (numbersList == null || numbersList.size() == 0) {
//            result.setMessage("文件内容为空");
//            return result;
//        }
//        //批量添加床位信息
//        IdGenerate idGenerate=new IdGenerate();
//        for (Numbers numbers : numbersList){
//            //生成床位编号number
//            //1.将生成number需要的信息放到Numbers中储存
//            Bed bed = new Bed();
//            if(numbers.getPatientinformationId()!=null) {
//                bed.setPatientInformationId(numbers.getPatientinformationId());
//            }
//            bed.setRoomId(numbers.getRoomId());
//            bed.setBedType(numbers.getBedType());
//            bed.setBedStatus(numbers.getBedStatus());
//            //2.调用NumberGenerate生成number
//            bed.setNumber(NumberGenerate.nextNumbers(numbers));
//            //查询添加的床位的number是否重复
//            //去除重复的信息
//            if (Db.lambdaQuery(Bed.class).eq(Bed::getNumber, bed.getNumber()).exists()) {
//                continue;
//            }
//            //
//            //插入床位信息
//            bed.setCreateBy(LoginConfig.class.getName());//创建人信息
//            bed.setCreateTime(LocalDateTime.now());//创建时间
//            String uuid=idGenerate.nextUUID(bed);
//            bed.setBedId(uuid);//生成id
//            //判断床位是否有病人
//            if (bed.getPatientInformationId() != null) {
//                //床位存在病人，直接填写病床使用日志
//                Bedlog bedlog = new Bedlog();
//                bedlog.setBedId(uuid);
//                bedlog.setPatientInfomationId(bed.getPatientInformationId());
//                bedlog.setUsingFlag(1);//添加进来的床位有病人关联说明床位是被占用的
//                bedlog.setCreateTime(LocalDateTime.now());
//                bedlog.setCreateBy(LoginConfig.class.getName());
//                bedlog.setId(idGenerate.nextUUID(bedlog));
//            }
//            bedsMapper.insert(bed);
//        }
//        result.setStatus(true);
//        result.setMessage("添加成功");
//        return result;
//    }

    @Override
    public List<RoomListVO> getRoomList() {
        List<RoomListVO> voList=roomsMapper.roomList();
        return voList;
    }
}