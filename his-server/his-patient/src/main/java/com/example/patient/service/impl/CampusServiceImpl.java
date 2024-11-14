package com.example.patient.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.patient.entity.Campus;
import com.example.patient.entity.Office;
import com.example.patient.entity.Ward;
import com.example.patient.mapper.CampusMapper;
import com.example.patient.mapper.OfficeMapper;
import com.example.patient.mapper.WardMapper;
import com.example.patient.service.ICampusService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.vo.patient.CampusVO;
import com.example.vo.patient.OfficeVo;
import com.example.vo.systemmanagement.ward.WardVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xw
 * @since 2024-05-09
 */
@Service
public class CampusServiceImpl extends ServiceImpl<CampusMapper, Campus> implements ICampusService {

    @Autowired
    private CampusMapper campusMapper;
    @Autowired
    private OfficeMapper officeMapper;
    @Autowired
    private WardMapper wardMapper;

    @Override
    public List<CampusVO> all_dep_search() {
        List<Campus> campusList=campusMapper.selectList(null);


        List<CampusVO> campusVOList=new ArrayList<>();
        for(Campus campus:campusList){
            CampusVO vo=new CampusVO();
            vo.setCampusId(campus.getId());
            vo.setCampusName(campus.getName());
            List<Office> officeList=officeMapper.selectList(new LambdaQueryWrapper<Office>()
                    .eq(Office::getCampusId,campus.getId()));
            List<OfficeVo> officeVoList=new ArrayList<>();
            for(Office office:officeList){
                OfficeVo officeVo=new OfficeVo();
                officeVo.setOfficeId(office.getId());
                officeVo.setOfficeName(office.getName());
                List<Ward> wardList=wardMapper.selectList(new LambdaQueryWrapper<Ward>()
                        .eq(Ward::getOfficeId,office.getId()));
                List<WardVo> wardVoList=new ArrayList<>();
                for (Ward ward:wardList){
                    WardVo wardVo=new WardVo();
                    wardVo.setWardId(ward.getId());
                    wardVo.setWardName(ward.getName());
                    wardVoList.add(wardVo);
                }
                officeVo.setWards(wardVoList);
                officeVoList.add(officeVo);
            }
            vo.setOffices(officeVoList);
            campusVOList.add(vo);

        }
        return campusVOList;
    }
}
