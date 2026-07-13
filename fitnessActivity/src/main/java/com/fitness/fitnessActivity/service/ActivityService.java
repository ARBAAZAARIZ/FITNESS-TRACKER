package com.fitness.fitnessActivity.service;

import com.fitness.fitnessActivity.dto.ActivityReqRes;
import com.fitness.fitnessActivity.dto.ApiResponse;

import java.util.List;

public interface ActivityService {

     ActivityReqRes saveActivity(ActivityReqRes request);
    List<ActivityReqRes> findAllActivities ();

     ApiResponse getUserActivity(String userId);
     ActivityReqRes getActivityByID(String id);
}
