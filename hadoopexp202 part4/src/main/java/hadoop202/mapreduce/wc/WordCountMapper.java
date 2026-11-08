package hadoop202.mapreduce.wc;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    @Override
    protected void map(LongWritable key1, Text v1, Context context)
            throws IOException, InterruptedException {
        // 将输入的一行文本转换为字符串
        String data = v1.toString();
        // 按空格分词
        String[] words = data.split(" ");
        // 输出<单词, 1>键值对
        for (String w : words) {
            context.write(new Text(w), new IntWritable(1));
        }
    }
}