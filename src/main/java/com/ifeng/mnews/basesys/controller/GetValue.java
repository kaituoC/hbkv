package com.ifeng.mnews.basesys.controller;

import com.ifeng.mnews.basesys.service.HBaseService;
import com.ifeng.mnews.basesys.utils.VersionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by changkt on 2018/1/18.
 */
@RestController
public class GetValue {
    private static final Logger logger = LoggerFactory.getLogger(GetValue.class);
//    private static final String location = VersionUtil.getPREFIX() + VersionUtil.getVERSION();
    private static final String location = VersionUtil.PREFIX + VersionUtil.VERSION;

    @RequestMapping(value = location + "/getByRowkey", method = RequestMethod.GET)
    public String getValue(String tableName, String rowkey) {
        long startTime = System.currentTimeMillis();
        HBaseService hBaseService = new HBaseService();
        String value = null;
        try {
            value = hBaseService.get(tableName, rowkey);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("RowKey=" + rowkey + ";Value=" + value);
        long endTime = System.currentTimeMillis();
        logger.info("totalTime:" + (endTime - startTime) + " ms");
        return "RowKey=" + rowkey + ";Value=" + value;
    }
}
