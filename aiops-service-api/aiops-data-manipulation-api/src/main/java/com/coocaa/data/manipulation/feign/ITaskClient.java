package com.coocaa.data.manipulation.feign;

import com.coocaa.common.request.RequestBean;
import com.coocaa.core.secure.constant.AppConstant;
import com.coocaa.core.tool.api.R;
import com.coocaa.data.manipulation.entity.Task;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @program: intelligent_maintenance
 * @description: 远程定时任务调用Feign
 * @author: dongyang_wu
 * @create: 2019-08-01 13:22
 */
@FeignClient(
        value = AppConstant.APPLICATION_TASK_NAME
)
public interface ITaskClient {
    String API_PREFIX = "/dataManipulation";

    /**
     * 新建定时任务
     *
     * @return
     */
    @PostMapping(API_PREFIX + "/create-task")
    R<Boolean> createTask(@RequestBody Task task, @RequestParam Integer type);

    @PostMapping(API_PREFIX + "/remove-task")
    R<Boolean> removeTask(@RequestBody RequestBean requestbean, @RequestParam Integer type);
}