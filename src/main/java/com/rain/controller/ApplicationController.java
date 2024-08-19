package com.rain.controller;


import com.rain.domain.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制器
 *
 * @author xueyu
 */
@Slf4j
@RestController
public class ApplicationController {

    /**
     * / 主路径
     *
     * @return R
     */
    @RequestMapping(value = "/")
    public R<?> rootPath() {
        return R.success("程序启动成功!");
    }

}
