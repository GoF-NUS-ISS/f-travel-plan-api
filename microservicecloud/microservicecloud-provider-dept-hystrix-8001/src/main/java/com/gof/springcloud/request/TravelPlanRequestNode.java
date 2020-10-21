package com.gof.springcloud.request;

import com.gof.springcloud.model.TravelPlanModel_DayNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@ApiModel(value = "node")
public class TravelPlanRequestNode {

    @ApiModelProperty(value = "type")
    private String type;

    @ApiModelProperty(value = "depart from")
    private String from;

    @ApiModelProperty(value = "arrive at")
    private String to;

    @ApiModelProperty(value = "start on")
    private String startOn;

    @ApiModelProperty(value = "return date")
    private String stopAt;

    @ApiModelProperty(value = "transport mode")
    private String transportMode;

    @ApiModelProperty(value = "cost")
    private double cost;

    @ApiModelProperty(value = "activity category")
    private String category;

    private double costActivity;

    @ApiModelProperty(value="rating")
    private int rating;

    @ApiModelProperty(value="review")
    private String review;

    @ApiModelProperty(value="location")
    private String location;

    @ApiModelProperty(value = "time start")
    private String timeStart;

    @ApiModelProperty(value = "time end")
    private String timeEnd;
}
