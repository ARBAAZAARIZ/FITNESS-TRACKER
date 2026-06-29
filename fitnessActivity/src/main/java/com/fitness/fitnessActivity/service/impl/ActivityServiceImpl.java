package com.fitness.fitnessActivity.service.impl;


import com.fitness.fitnessActivity.dto.ActivityReqRes;
import com.fitness.fitnessActivity.dto.ApiResponse;
import com.fitness.fitnessActivity.microservice.UserInteractService;
import com.fitness.fitnessActivity.model.Activity;
import com.fitness.fitnessActivity.repository.ActivityRepository;
import com.fitness.fitnessActivity.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    UserInteractService userInteractService;

    @Override
    public ActivityReqRes trackActivity(ActivityReqRes request){
        Map userResponse;
        try{
             userResponse = userInteractService.validateUser(request.getUserId());
        } catch (Exception e) {
           return ActivityReqRes.builder()
                   .apiStatus(false)
                   .message("Failed to validate user, please Login again " +
                           "or if same problem occurs contact admin")
                   .build();
        }

        if(userResponse.get("data").equals("false")){
            return ActivityReqRes.builder()
                    .apiStatus(false)
                    .message("Failed to validate user, please Login again " +
                            "or if same problem occurs contact admin")
                    .build();
        }

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

    @Override
    public ApiResponse  getUserActivity(String userId) {

        ApiResponse response = new ApiResponse();
        try{
            List<Activity> activities = activityRepository.findByUserId(userId);

            response.setApiStatus(true);
            response.setMessage("User activity retrieved successfully");
            if(activities.size()>0){
                response.setData(activities.stream()
                        .map(activity -> mapToResponse(activity))
                        .collect(Collectors.toList()));
            }else{
                response.setData(new ArrayList<>());
                response.setMessage("No activity found for user");
                response.setApiStatus(true);
            }

            return response;

        }catch(Exception e){
            log.info( " Error while getting user activity "+ e.getMessage());
            response.setApiStatus(false);
            response.setMessage("Failed to get user activity");
            return response;
        }
    }


    public ActivityReqRes getActivityByID(String id){
        Activity activity = activityRepository.findById(id).orElse(null);
        ActivityReqRes response =  new ActivityReqRes();
        if(activity==null){
            response.setApiStatus(false);
            response.setMessage("Activity Not found With this Id, Kindly contact Admin");
            return response;
        }

        response = mapToResponse(activity);
        response.setApiStatus(true);
        response.setMessage("Activity Fetched Successfully");
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
