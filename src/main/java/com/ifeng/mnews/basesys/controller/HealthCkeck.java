package com.ifeng.mnews.basesys.controller;

import com.ifeng.mnews.basesys.utils.VersionUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by changkt on 2018/1/18.
 */
@RestController
public class HealthCkeck {
    private static final String MAPPING = VersionUtil.PREFIX + VersionUtil.HEALTH_CHECK;

    @RequestMapping(value = MAPPING, method = RequestMethod.GET)
    public String healthCheck(){
        return "I'm ok at " + System.currentTimeMillis();
    }
}
