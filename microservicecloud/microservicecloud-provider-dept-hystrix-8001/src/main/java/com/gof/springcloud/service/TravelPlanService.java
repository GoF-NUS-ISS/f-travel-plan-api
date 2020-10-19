package com.gof.springcloud.service;

import java.util.List;

import com.gof.springcloud.model.TravelPlanModel;

public interface TravelPlanService {

	TravelPlanModel addPlan(TravelPlanModel travelPlanModel);

	TravelPlanModel getById(String id);

	List<TravelPlanModel> getByName(String name);

	String addPlanBuilder(TravelPlanModel travelPlanModel);

}
