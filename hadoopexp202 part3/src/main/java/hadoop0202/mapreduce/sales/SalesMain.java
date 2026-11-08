package hadoop0202.mapreduce.sales;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;

public class SalesMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        int argLength = otherArgs.length;

        // 检查参数数量
        if (argLength < 2) {
            System.err.println("Usage: salesstat <in> [<in>...] <out>");
            System.exit(2);
        }

        // 创建Job实例
        Job job = Job.getInstance(conf, "Sales Yearly Total");
        // 设置主类
        job.setJarByClass(SalesMain.class);
        // 设置Mapper和Reducer
        job.setMapperClass(SalesMapper.class);
        job.setReducerClass(SalesReducer.class);
        // 设置Combiner（本地聚合，大幅提升性能）
        job.setCombinerClass(SalesReducer.class);
        // 设置输出键值对类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(DoubleWritable.class);

        // 添加输入路径
        for (int i = 0; i < argLength - 1; i++) {
            FileInputFormat.addInputPath(job, new Path(otherArgs[i]));
        }
        // 设置输出路径
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[argLength - 1]));

        // 提交作业并等待完成
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}