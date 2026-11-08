package hadoop202.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.junit.Test;

import java.net.URI;

public class HdfsUtilsTest {

    private static final String HDFS_URI = "hdfs://10.0.0.71:8020";
    private Configuration conf = new Configuration();

    @Test
    public void testCreate() {
        String filePath = "/202/test.txt";
        String content = "hello hadoop from 202";
        boolean rs = false;
        try {
            URI uri = new URI(HDFS_URI);
            rs = HdfsUtils.createFile(conf, uri, filePath, content, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(rs ? "✅ 创建文件成功" : "❌ 创建文件失败");
    }

    @Test
    public void testUpload() {
        // 本地文件路径（替换为你电脑上的实际路径）
        String localPath = "D:/test/upload.txt";
        String hdfsPath = "/202/upload.txt";
        boolean rs = false;
        try {
            URI uri = new URI(HDFS_URI);
            rs = HdfsUtils.uploadFile(conf, uri, localPath, hdfsPath, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(rs ? "✅ 上传文件成功" : "❌ 上传文件失败");
    }

    @Test
    public void testDownload() {
        String hdfsPath = "/202/test.txt";
        // 本地保存路径（替换为你电脑上的实际路径）
        String localPath = "D:/test/download.txt";
        boolean rs = false;
        try {
            URI uri = new URI(HDFS_URI);
            rs = HdfsUtils.downloadFile(conf, uri, hdfsPath, localPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(rs ? "✅ 下载文件成功" : "❌ 下载文件失败");
    }
}