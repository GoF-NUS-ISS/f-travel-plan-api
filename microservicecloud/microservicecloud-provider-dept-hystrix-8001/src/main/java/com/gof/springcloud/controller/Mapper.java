package com.gof.springcloud.controller;

import com.gof.springcloud.model.*;
import com.gof.springcloud.request.TravelPlanRequestDay;
import com.gof.springcloud.request.TravelPlanRequestMain;
import com.gof.springcloud.request.TravelPlanRequestNode;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Mapper {
    private static final DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter nodeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public static TravelPlanRequestMain ModelToRequest(TravelPlanModel travelPlanModel){
        log.info("travelPlanModel {}", travelPlanModel);
        TravelPlanRequestMain travelPlanRequestMain = new TravelPlanRequestMain();
        travelPlanRequestMain.setId(travelPlanModel.getId());
        travelPlanRequestMain.setName(travelPlanModel.getName());
        travelPlanRequestMain.setTitle(travelPlanModel.getTitle());

        for(TravelPlanModel_Day travelPlanModel_day: travelPlanModel.getDays()){
            TravelPlanRequestDay travelPlanRequestDay = new TravelPlanRequestDay();

            travelPlanRequestDay.setDate(travelPlanModel_day.getDate().toLocalDate().format(dayFormat));

            for(TravelPlanModel_DayNode travelPlanModelDayNode: travelPlanModel_day.getNodes()){

                TravelPlanRequestNode travelPlanRequestNode = new TravelPlanRequestNode();
                if(travelPlanModelDayNode instanceof TravelPlanModel_Activity){
                    travelPlanRequestNode.setType("activity");
                    travelPlanRequestNode.setCategory(((TravelPlanModel_Activity) travelPlanModelDayNode).getCategory());
                    travelPlanRequestNode.setCostActivity(((TravelPlanModel_Activity) travelPlanModelDayNode).getCost());
                    travelPlanRequestNode.setRating(((TravelPlanModel_Activity) travelPlanModelDayNode).getRating());
                    travelPlanRequestNode.setReview(((TravelPlanModel_Activity) travelPlanModelDayNode).getReview());
                    travelPlanRequestNode.setLocation(((TravelPlanModel_Activity) travelPlanModelDayNode).getLocation());
                    travelPlanRequestNode.setTimeStart(((TravelPlanModel_Activity) travelPlanModelDayNode).getTimeStart().format(nodeFormat));
                    travelPlanRequestNode.setTimeEnd(((TravelPlanModel_Activity) travelPlanModelDayNode).getTimeEnd().format(nodeFormat));
                }
                if(travelPlanModelDayNode instanceof TravelPlanModel_Leg){
                    travelPlanRequestNode.setType("leg");
                    travelPlanRequestNode.setFrom(((TravelPlanModel_Leg) travelPlanModelDayNode).getFrom());
                    travelPlanRequestNode.setTo(((TravelPlanModel_Leg) travelPlanModelDayNode).getTo());
                    travelPlanRequestNode.setStartOn(((TravelPlanModel_Leg) travelPlanModelDayNode).getStartOn().format(nodeFormat));
                    travelPlanRequestNode.setStopAt(((TravelPlanModel_Leg) travelPlanModelDayNode).getStopAt().format(nodeFormat));
                    travelPlanRequestNode.setTransportMode(((TravelPlanModel_Leg) travelPlanModelDayNode).getTransportMode());
                    travelPlanRequestNode.setCost(((TravelPlanModel_Leg) travelPlanModelDayNode).getCost());
                }
                travelPlanRequestDay.getNodes().add(travelPlanRequestNode);
            }
            travelPlanRequestMain.getDays().add(travelPlanRequestDay);
        }
        log.info("travelPlanRequestMain {}", travelPlanRequestMain);
        return travelPlanRequestMain;
    }

    public static TravelPlanModel RequestToModel(TravelPlanRequestMain travelPlanRequestMain){
        log.info("travelPlanRequestMain {}", travelPlanRequestMain);
        TravelPlanModel travelPlanModel = new TravelPlanModel();
        if(travelPlanRequestMain.getId() != null && !travelPlanRequestMain.getId().isEmpty()){
            travelPlanModel.setId(travelPlanRequestMain.getId());
        }
        travelPlanModel.setName(travelPlanRequestMain.getName());
        travelPlanModel.setTitle(travelPlanRequestMain.getTitle());
        List<TravelPlanModel_Day> travelPlanModel_dayList = new ArrayList<>();
        for(TravelPlanRequestDay travelPlanRequestDay: travelPlanRequestMain.getDays()){

            TravelPlanModel_Day travelPlanModelDay = new TravelPlanModel_Day();
            travelPlanModelDay.setDate(LocalDate.parse(travelPlanRequestDay.getDate(), dayFormat).atStartOfDay());

            List<TravelPlanModel_DayNode> travelPlanModelDayNodes = new ArrayList<>();
            for(TravelPlanRequestNode travelPlanRequestNode: travelPlanRequestDay.getNodes()){
                TravelPlanModel_DayNode travelPlanModelDayNode = null;
                if(travelPlanRequestNode.getType().equalsIgnoreCase("activity")){
                    travelPlanModelDayNode = new TravelPlanModel_Activity();
                    ((TravelPlanModel_Activity)travelPlanModelDayNode).setCategory(travelPlanRequestNode.getCategory());
                    ((TravelPlanModel_Activity)travelPlanModelDayNode).setCost(travelPlanRequestNode.getCostActivity());
                    ((TravelPlanModel_Activity)travelPlanModelDayNode).setLocation(travelPlanRequestNode.getLocation());
                    ((TravelPlanModel_Activity)travelPlanModelDayNode).setRating(travelPlanRequestNode.getRating());
                    ((TravelPlanModel_Activity)travelPlanModelDayNode).setReview(travelPlanRequestNode.getReview());
                    ((TravelPlanModel_Activity)travelPlanModelDayNode).setTimeStart(LocalDateTime.parse(travelPlanRequestNode.getTimeStart(), nodeFormat));
                    ((TravelPlanModel_Activity)travelPlanModelDayNode).setTimeEnd(LocalDateTime.parse(travelPlanRequestNode.getTimeEnd(), nodeFormat));
                }
                if(travelPlanRequestNode.getType().equalsIgnoreCase("leg")){
                    travelPlanModelDayNode = new TravelPlanModel_Leg();
                    ((TravelPlanModel_Leg)travelPlanModelDayNode).setCost(travelPlanRequestNode.getCost());
                    ((TravelPlanModel_Leg)travelPlanModelDayNode).setFrom(travelPlanRequestNode.getFrom());
                    ((TravelPlanModel_Leg)travelPlanModelDayNode).setTo(travelPlanRequestNode.getTo());
                    ((TravelPlanModel_Leg)travelPlanModelDayNode).setTransportMode(travelPlanRequestNode.getTransportMode());
                    ((TravelPlanModel_Leg)travelPlanModelDayNode).setStartOn(LocalDateTime.parse(travelPlanRequestNode.getStartOn(), nodeFormat));
                    ((TravelPlanModel_Leg)travelPlanModelDayNode).setStopAt(LocalDateTime.parse(travelPlanRequestNode.getStopAt(), nodeFormat));
                }
                travelPlanModelDayNodes.add(travelPlanModelDayNode);
            }
            travelPlanModelDay.setNodes(travelPlanModelDayNodes);
            travelPlanModel_dayList.add(travelPlanModelDay);
        }
        travelPlanModel.setDays(travelPlanModel_dayList);
        log.info("travelPlanModel {}", travelPlanModel);
        return travelPlanModel;
    }
}
