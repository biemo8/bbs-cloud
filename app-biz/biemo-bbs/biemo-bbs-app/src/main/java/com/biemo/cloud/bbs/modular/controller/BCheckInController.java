package com.biemo.cloud.bbs.modular.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.biemo.cloud.bbs.api.BCheckInApi;
import com.biemo.cloud.bbs.modular.context.BiemoLoginContext;
import com.biemo.cloud.bbs.modular.domain.BCheckIn;
import com.biemo.cloud.bbs.modular.domain.BUser;
import com.biemo.cloud.bbs.modular.service.IBCheckInService;
import com.biemo.cloud.bbs.modular.service.IBUserService;
import com.biemo.cloud.core.util.DateUtils;
import com.biemo.cloud.core.util.StringUtils;
import com.biemo.cloud.core.util.uuid.IdUtils;
import com.biemo.cloud.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/checkin")
public class BCheckInController extends BaseController implements BCheckInApi {

    @Autowired
    IBCheckInService ibCheckInService;

    @Autowired
    IBUserService ibUserService;

    @Override
    public ResponseData getCheckin(){
        BUser bUser = BiemoLoginContext.me().getCurrentUser();
        if(bUser!=null){
            BCheckIn bCheckIn = new BCheckIn();
            bCheckIn.setUserId(bUser.getId());
            BCheckIn bCheckInNew = ibCheckInService.getOne(new QueryWrapper<>(bCheckIn));
            if(bCheckInNew!=null){
                String latestDayName = bCheckInNew.getLatestDayName()!=null?bCheckInNew.getLatestDayName().toString():null;
                Date latestDate = DateUtils.parse(latestDayName,"yyyyMMdd");
                Date now = new Date();
                long day = DateUtils.getDatePoorDay(now,latestDate);
                if(day==0){
                    JSONObject jsonObject = (JSONObject) JSONObject.toJSON(bCheckInNew);
                    jsonObject.put("checkIn",true);
                    return ResponseData.success(jsonObject);
                }
            }
        }
        return ResponseData.success();
    }

    @Override
    public ResponseData doCheckin(){
        BUser bUser = BiemoLoginContext.me().getCurrentUser();
        if(bUser==null){
            return ResponseData.error("请先登录！");
        }
        BCheckIn bCheckIn = new BCheckIn();
        bCheckIn.setUserId(bUser.getId());
        BCheckIn bCheckInNew = ibCheckInService.getOne(new QueryWrapper<>(bCheckIn));
        if(bCheckInNew!=null){
            String latestDayName = bCheckInNew.getLatestDayName()!=null?bCheckInNew.getLatestDayName().toString():null;
            Date latestDate = DateUtils.parse(latestDayName,"yyyyMMdd");
            Date now = new Date();
            long day = DateUtils.getDatePoorDay(now,latestDate);
            bCheckInNew.setUpdateTime(System.currentTimeMillis());
            String latestDayNameNew = DateUtils.parseDateToStr("yyyyMMdd",new Date());
            bCheckInNew.setLatestDayName(Long.valueOf(latestDayNameNew));
            if(day==1){
                bUser.setScore(bUser.getScore()+1);
                bCheckInNew.setConsecutiveDays(bCheckInNew.getConsecutiveDays()+1);
            }else if(day==0){
                //重复签到不处理
            }else{
                bCheckInNew.setConsecutiveDays(1L);
                bUser.setScore(bUser.getScore()+1);
            }
            ibUserService.updateById(bUser);
            ibCheckInService.updateById(bCheckInNew);
        }else{
            bCheckInNew = new BCheckIn();
            bCheckInNew.setConsecutiveDays(1L);
            Long currentTime = System.currentTimeMillis();
            bCheckInNew.setCreateTime(currentTime);
            bCheckInNew.setUpdateTime(currentTime);
            String latestDayName = DateUtils.parseDateToStr("yyyyMMdd",new Date());
            bCheckInNew.setLatestDayName(Long.valueOf(latestDayName));
            bCheckInNew.setUserId(bUser.getId());
            bCheckInNew.setId(IdUtils.fastUUIDLong());
            ibCheckInService.save(bCheckInNew);

            bUser.setScore(bUser.getScore()+1);
            ibUserService.updateById(bUser);
        }
        return ResponseData.success();
    }

    @Override
    public ResponseData rank(){
        QueryWrapper<BCheckIn> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("consecutive_days");
        queryWrapper.last("limit 10");
        List<BCheckIn> bCheckInList = ibCheckInService.list(queryWrapper);
        if(bCheckInList!=null&&bCheckInList.size()>0){
            List<JSONObject> jsonObjectList = new ArrayList<>();
            bCheckInList.forEach(checkin->{
                BUser bUser = ibUserService.getById(checkin.getUserId());
                JSONObject jsonObject = (JSONObject) JSONObject.toJSON(checkin);
                jsonObject.put("user",bUser);
                jsonObjectList.add(jsonObject);
            });
            return ResponseData.success(jsonObjectList);
        }
        return ResponseData.success();
    }

}
