package com.fitness.fitnessActivity.service;

import com.fitness.fitnessActivity.dto.ActivityReqRes;

import java.util.List;

public interface ActivityService {

    public ActivityReqRes trackActivity(ActivityReqRes request);
    List<ActivityReqRes> findAllActivities ();

}
