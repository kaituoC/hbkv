package com.ifeng.mnews.basesys.service;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.MessageDigest;

/**
 * Created by changkt on 2018/1/18.
 */
@Service
public class HBaseService {
    private static final Logger logger = LoggerFactory.getLogger(HBaseService.class);

    private Configuration conf = null;

/*    static {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("hbase.zookeeper.quorum", "10.21.6.86,10.21.6.87,10.21.6.88");
//        conf.set("hbase.zookeeper.quorum", "10.90.11.60,10.90.11.105,10.90.11.30");
//        conf.set("hbase.zookeeper.property.clientPort", "2181");
    }*/

    public void addData(String rowKey, String tableName, String[] column1, String[] value1) throws IOException {
        // 设置rowkey
        Put put = new Put(Bytes.toBytes(rowKey));
        // HTabel负责跟记录相关的操作如增删改查等
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        // 获取所有的列族
        HColumnDescriptor[] columnFamilies = table.getTableDescriptor().getColumnFamilies();
        for (int i = 0; i < columnFamilies.length; i++) {
            // 获取列族名
            String familyName = columnFamilies[i].getNameAsString();
            // info列族put数据
//            if (familyName.equals("article")) {
            if (familyName.equals("info")) {
                for (int j = 0; j < column1.length; j++) {
                    put.add(Bytes.toBytes(familyName), Bytes.toBytes(column1[j]), Bytes.toBytes(value1[j]));
                }
            }
        }
        table.put(put);
        System.out.println("add data Success!");
    }

    /**
     * 根据rwokey查询
     *
     * @rowKey rowKey
     * @tableName 表名
     */
    public Result getResult(String tableName, String rowKey)
            throws IOException {
        Get get = new Get(Bytes.toBytes(rowKey));
        logger.info("get from table:" + tableName + ", rowkey=" + rowKey);
//        HTable table = new HTable(conf, Bytes.toBytes(tableName));// 获取表
        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf(tableName));
        Result result = table.get(get);
        return result;
    }


    /**
     * 打印单条结果集
     *
     * @param result
     */
    public void printResult(Result result) {
        for (KeyValue kv : result.list()) {
            System.out.println("family:" + Bytes.toString(kv.getFamily()));
            System.out.println("qualifier:" + Bytes.toString(kv.getQualifier()));
            System.out.println("value:" + Bytes.toString(kv.getValue()));
            System.out.println("Timestamp:" + kv.getTimestamp());
            System.out.println("-------------------------------------------");
        }
    }

    public String get(String tableName, String rowKey) throws IOException {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("hbase.zookeeper.quorum", "10.21.6.86,10.21.6.87,10.21.6.88");

        // 创建表
//        String tableName = "liuTest";
//        String tableName = "news_itemf";
//        String tableName = "kktest";
//        String rowKey = getRowKey("004401723756017");
        String[] column1 = {"title", "content", "tag"};
        String[] value1 = {
                "Head First HBase",
                "HBase is the Hadoop database. Use it when you need random, realtime read/write access to your Big Data.",
                "Hadoop,HBase,NoSQL"};
//        addData(rowKey, tableName, column1, value1);
//        return getResult(tableName, "1050_004401723756017").toString();
        return getResult(tableName, rowKey).toString();
    }

    private String getRowKey(String key) {

        try {
            byte[] btInput = key.getBytes();
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(btInput);
            byte[] resultByteArray = messageDigest.digest();
            int i = 0;
            for (int offset = 0; offset < resultByteArray.length; offset++) {
                i += Math.abs(resultByteArray[offset]);
            }
            int prefix = 1000 + i % 1000;
            String rowKey = "" + prefix + "_" + key;
            System.out.println("=========rowKey=" + rowKey + "=======");
            return rowKey;
        } catch (Exception e) {
        }
        return null;
    }


}
