package com.gof.springcloud.service.impl;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gof.springcloud.config.Topics;
import com.gof.springcloud.model.TravelPlanEvent;
import com.gof.springcloud.model.TravelPlanModel;
import com.gof.springcloud.repository.TravelPlanRepository;
import com.gof.springcloud.service.TravelPlanService;

import io.github.majusko.pulsar.producer.PulsarTemplate;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TravelPlanServiceImpl implements TravelPlanService {
	@Autowired
	private TravelPlanRepository travelPlanRepository;
	@Autowired
	private PulsarTemplate<String> eventJsonProducer;

	@Override
	@SneakyThrows
	public TravelPlanModel addPlan(TravelPlanModel travelPlanModel){
		travelPlanModel = travelPlanRepository.save(travelPlanModel);
		// set event entity
		TravelPlanEvent event = new TravelPlanEvent();
		event.setModel(travelPlanModel);
		event.setOptTime(new Date());
		event.setOptType("SAVE");

		// transfer into json
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		String eventJsonStr = objectMapper.writeValueAsString(event);
		eventJsonProducer.send(Topics.PLAN_EVENT_JSON, eventJsonStr);
		log.info("pulsar produce plan json: {}", eventJsonStr);

		return travelPlanModel;
	}

	@Override
	public String addPlanBuilder(TravelPlanModel travelPlanModel){
		return travelPlanRepository.save(travelPlanModel).getId();
	}

	@Override
	public List<TravelPlanModel> getByName(String name) {
		return travelPlanRepository.findByName(name);
	}

	@Override
	public TravelPlanModel getById(String id) {
		return travelPlanRepository.findById(id).get();
	}
}
