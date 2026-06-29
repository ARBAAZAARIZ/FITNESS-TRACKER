package com.fitness.fitnessActivity.controller;

import com.fitness.fitnessActivity.dto.ActivityReqRes;
import com.fitness.fitnessActivity.dto.ApiResponse;
import com.fitness.fitnessActivity.model.Activity;
import com.fitness.fitnessActivity.repository.ActivityRepository;
import com.fitness.fitnessActivity.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    @Autowired
    private ActivityRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoDatabaseFactory mongoDatabaseFactory;

    @Autowired
    private Environment env;


    @Autowired
    private ActivityService activityService;

    @PostMapping
    public ResponseEntity<?> trackActivity(@RequestBody ActivityReqRes request){
        ActivityReqRes response = activityService.trackActivity(request);

        return ResponseEntity.ok(response);

    }

    @GetMapping
    public ResponseEntity<?> getUserActivities(@RequestHeader("X-User-ID") String userId){
        ApiResponse response = activityService.getUserActivity(userId);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/{activityId}")
    public ResponseEntity<?> getActivityById(@PathVariable  String activityId){
        ActivityReqRes response = activityService.getActivityByID(activityId);

        return ResponseEntity.ok(response);

    }

    /*@GetMapping
    public List<Activity> getAll() {
        return repository.findAll();
    }*/

    @GetMapping("/count")
    public long count() {
        return repository.count();
    }

    @GetMapping("/db")
    public String db() {
        return mongoTemplate.getDb().getName();
    }

    @GetMapping("/factory")
    public String factory() {
        return mongoDatabaseFactory.getMongoDatabase().getName();
    }

    @GetMapping("/config")
    public Map<String, String> config() {

        Map<String, String> map = new HashMap<>();

        map.put("database", env.getProperty("spring.data.mongodb.database"));
        map.put("uri", env.getProperty("spring.data.mongodb.uri"));

        return map;
    }
}