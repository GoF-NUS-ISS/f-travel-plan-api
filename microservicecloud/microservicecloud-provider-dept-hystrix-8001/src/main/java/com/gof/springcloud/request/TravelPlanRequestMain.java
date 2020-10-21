package com.gof.springcloud.request;

import com.gof.springcloud.model.TravelPlanModel_Day;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value = "TravelPlanRequestMain", description = "Travel Plan Entity")
public class TravelPlanRequestMain {

    @ApiModelProperty(value = "title", required = true)
    private String title;

    @ApiModelProperty(value = "user name", required = true)
    private String name;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "days")
    private List<TravelPlanRequestDay> days=new ArrayList<>();
}
