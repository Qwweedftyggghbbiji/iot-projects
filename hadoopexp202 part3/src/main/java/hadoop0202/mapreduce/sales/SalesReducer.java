package hadoop0202.mapreduce.sales;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class SalesReducer extends Reducer<Text, DoubleWritable, Text, DoubleWritable> {
    @Override
    protected void reduce(Text k3, Iterable<DoubleWritable> v3, Context context)
            throws IOException, InterruptedException {
        // 统计同一年份的销售总额
        double total = 0.0;
        for (DoubleWritable v : v3) {
            total += v.get();
        }
        // 输出<年份, 总销售额>
        context.write(k3, new DoubleWritable(total));
    }
}