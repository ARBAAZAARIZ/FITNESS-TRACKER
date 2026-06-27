package com.fitness.fitnessActivity.repository;

import com.fitness.fitnessActivity.model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActivityRepository extends MongoRepository<Activity, String> {
}