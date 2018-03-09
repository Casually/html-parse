package cc.casually.htmlParse.util;

import java.io.*;

/**
 * 文件管理工具类
 * @author 13545
 */
public class FileUtil {

    private String filePath;
    private File file;
    private FileInputStream fileInputStream ;
    private FileOutputStream fileOutputStream;
    private OutputStreamWriter outputStreamWriter;
    private BufferedWriter bufferedWriter;

    public FileUtil(String filePath) throws Exception {
        this.filePath = filePath;
        this.file = new File(filePath);
        this.fileOutputStream = new FileOutputStream(filePath);
        this.outputStreamWriter = new OutputStreamWriter(this.fileOutputStream, "UTF-8");
        this.bufferedWriter = new BufferedWriter(this.outputStreamWriter);
    }

    /**
     * 一次写入多行数据
     * @param str
     * @throws Exception
     */
    public void writeStrArray(String[] str) throws Exception {
        for (String str1:str) {
            writeLineStr(str1);
        }
        close();
    }

    /**
     * 写入数据
     * @param str
     * @throws Exception
     */
    public void writeLineStr(String str) throws Exception{
        bufferedWriter.write(str + "\r\n");
    }

    /**
     * 关闭
     * @throws IOException
     */
    public void close(){
        try {
            this.bufferedWriter.close();
            this.outputStreamWriter.close();
            this.fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
