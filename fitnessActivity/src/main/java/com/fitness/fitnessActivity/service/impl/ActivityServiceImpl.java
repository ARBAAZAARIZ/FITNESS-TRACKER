package com.fitness.fitnessActivity.service.impl;


import com.fitness.fitnessActivity.dto.ActivityReqRes;
import com.fitness.fitnessActivity.model.Activity;
import com.fitness.fitnessActivity.repository.ActivityRepository;
import com.fitness.fitnessActivity.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityRepository activityRepository;

    @Override
    public ActivityReqRes trackActivity(ActivityReqRes request){

        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();
        ActivityReqRes response = new ActivityReqRes();

        try{
            Activity savedActivity = activityRepository.save(activity);
            if(savedActivity.getId()!=null){
                response = mapToResponse(savedActivity);
                response.setApiStatus(true);
                response.setMessage("Activity tracked successfully");
                return response;
            }else{
                response.setApiStatus(false);
                response.setMessage("Failed to save track activity");
                return response;
            }

        }catch (Exception e){
            log.info( " Error while saving tracking activity "+ e.getMessage());
            response.setApiStatus(false);
            response.setMessage("Failed to track activity");
            return response;
        }
    }

    @Override
    public List<ActivityReqRes> findAllActivities() {
        List <Activity> activities =  activityRepository.findAll();

        ActivityReqRes singleRes = new ActivityReqRes();

        List<ActivityReqRes> response = new ArrayList<>();

        for(Activity activity : activities){
            singleRes = mapToResponse(activity);
            response.add(singleRes);
        }

        return response;

    }

    private ActivityReqRes mapToResponse(Activity activity){

        ActivityReqRes response = new ActivityReqRes();

        response.setId(activity.getId());
        response.setUserId(activity.getUserId());
        response.setType(activity.getType());
        response.setDuration(activity.getDuration());
        response.setCaloriesBurned(activity.getCaloriesBurned());
        response.setStartTime(activity.getStartTime());
        response.setAdditionalMetrics(activity.getAdditionalMetrics());
        response.setCreatedAt(activity.getCreatedAt());
        response.setUpdatedAt(activity.getUpdatedAt());

        return response;


    }

}
