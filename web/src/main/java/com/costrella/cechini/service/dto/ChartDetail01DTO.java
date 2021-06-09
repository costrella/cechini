package com.costrella.cechini.service.dto;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link com.costrella.cechini.domain.Worker} entity.
 */
@ApiModel(description = "chart 01")
public class ChartDetail01DTO implements Serializable {

    public String label;
    public List<Integer> data;
    public int lineTension = 0;
    public String backgroundColor = "transparent";
    public String borderColor = "#007bff";
    public int borderWidth = 4;
    public String pointBackgroundColor = "#007bff";

}
