package com.gof.springcloud.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gof.springcloud.model.TravelPlanModel_DayNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "day")
public class TravelPlanRequestDay {
    @ApiModelProperty(value = "current date")
    private String date;

    @ApiModelProperty(value = "nodes")
    private List<TravelPlanRequestNode> nodes=new ArrayList<>();
}
