package com.zhao.eduservice.entity.Vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 小节
 */
@Data
public class VideoVo {

    private String id;

    private String title;

    @ApiModelProperty(value = "云端视频资源")
    private String videoSourceId;
}
