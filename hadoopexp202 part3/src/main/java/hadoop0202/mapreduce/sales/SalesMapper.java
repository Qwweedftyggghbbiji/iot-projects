package hadoop0202.mapreduce.sales;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class SalesMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {
    @Override
    protected void map(LongWritable key1, Text v1, Context context)
            throws IOException, InterruptedException {
        // 将一行数据转换为字符串
        String data = v1.toString();
        // 按逗号分割CSV字段
        String[] fields = data.split(",");

        // 跳过表头行和格式错误的行（确保有7个字段）
        if (fields.length < 7) {
            return;
        }

        try {
            // 提取年份：TIME字段是第3个字段（索引2），格式为"1998-01-01"
            String year = fields[2].split("-")[0];
            // 提取销售总额：AMOUNT_SOLD字段是第7个字段（索引6）
            double amount = Double.parseDouble(fields[6]);

            // 输出<年份, 销售额>键值对
            context.write(new Text(year), new DoubleWritable(amount));
        } catch (Exception e) {
            // 跳过解析错误的行
            System.err.println("跳过错误行：" + data);
        }
    }
}