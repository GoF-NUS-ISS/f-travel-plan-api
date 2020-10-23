package com.gof.springcloud.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gof.springcloud.entities.interaction.AjaxResponse;
import com.gof.springcloud.model.TravelPlanModel;
import com.gof.springcloud.request.TravelPlanRequestMain;
import com.gof.springcloud.service.TravelPlanService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api("Travel Plan API")
public class TravelPlanController
{
	private Logger log = LoggerFactory.getLogger(TravelPlanController.class);

	@Autowired
	private TravelPlanService service;

	@PostMapping("/travelPlan")
	@ApiOperation(value = "Add a travelPlan", notes = "Add a travelPlan")
	public AjaxResponse addPlan(@RequestBody TravelPlanRequestMain travelPlanRequestMain){
		return AjaxResponse.success(service.addPlan(Mapper.RequestToModel(travelPlanRequestMain)));
	}

	@PostMapping("/travelPlanBuilder")
    @CacheEvict(value = "travelPlanByName", key = "#travelPlan.name")
	@ApiOperation(value = "Add a travelPlan from builder", notes = "Add a travelPlan from builder")
	public String addPlanBuilder(@RequestBody TravelPlanModel travelPlan){
		return service.addPlanBuilder(travelPlan);
	}

	@GetMapping("/travelPlan/id/{id}")
	@ApiOperation(value = "Get travelPlan by id", notes = "Get travelPlan by id")
	public TravelPlanRequestMain getById(@PathVariable String id){
		return Mapper.ModelToRequest(service.getById(id));
	}

	@GetMapping("/travelPlan/name/{name}")
	@ApiOperation(value = "Get travelPlan by name", notes = "Get travelPlan by name")
	//@ApiImplicitParam(paramType = "name", required = true, defaultValue = "tim")
	@Cacheable(value = "travelPlanByName", key = "#name", unless = "#result == null || #result.size() == 0")
	public List<TravelPlanRequestMain> getByName(@PathVariable String name){
		return service
				.getByName(name)
				.stream()
				.map(Mapper::ModelToRequest).
						collect(Collectors.toList());
	}


}