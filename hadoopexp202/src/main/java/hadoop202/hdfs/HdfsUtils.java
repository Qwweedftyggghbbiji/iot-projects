package hadoop202.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.net.URI;

public class HdfsUtils {

    /**
     * 在HDFS上创建文件并写入内容
     */
    public static boolean createFile(Configuration conf, URI uri, String hdfsFilePath, String content, boolean overwrite) {
        FileSystem fs = null;
        FSDataOutputStream os = null;
        boolean rs = false;
        try {
            fs = FileSystem.get(uri, conf, "hadoop");
            Path dfs = new Path(hdfsFilePath);
            os = fs.create(dfs, overwrite);
            os.writeBytes(content);
            rs = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) os.close();
                if (fs != null) fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rs;
    }

    /**
     * 上传本地文件到HDFS
     */
    public static boolean uploadFile(Configuration conf, URI uri, String localFilePath, String hdfsFilePath, boolean overwrite) {
        FileSystem fs = null;
        boolean rs = false;
        try {
            fs = FileSystem.get(uri, conf, "hadoop");
            Path localPath = new Path(localFilePath);
            Path hdfsPath = new Path(hdfsFilePath);
            fs.copyFromLocalFile(false, overwrite, localPath, hdfsPath);
            rs = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fs != null) fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rs;
    }

    /**
     * 从HDFS下载文件到本地
     */
    public static boolean downloadFile(Configuration conf, URI uri, String hdfsFilePath, String localFilePath) {
        FileSystem fs = null;
        boolean rs = false;
        try {
            fs = FileSystem.get(uri, conf, "hadoop");
            Path hdfsPath = new Path(hdfsFilePath);
            Path localPath = new Path(localFilePath);
            fs.copyToLocalFile(false, hdfsPath, localPath);
            rs = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fs != null) fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rs;
    }
}