package com.costrella.cechini.service.dto;

import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * A DTO for the {@link com.costrella.cechini.domain.Worker} entity.
 */
@ApiModel(description = "chart 01")
public class Chart01DTO implements Serializable {


     public List<String> monthsName;

     public List<ChartDetail01DTO> details;






}
