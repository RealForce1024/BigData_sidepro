package com.fqc.hadoop.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by fqc on 2016/5/1.
 */
public class SimpleWordCountMapper extends Mapper<LongWritable,Text,Text,IntWritable>{

    /**
     *
     * @param key 文本偏移量
     * @param value 一行文本
     * @param context mr执行上下文
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] words = line.split(" ");
        for (String word : words) {
            context.write(new Text(word),new IntWritable(1));
        }
    }
}
