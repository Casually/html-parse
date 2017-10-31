package cc.casually.htmlParse.kuangshikeji;

import cc.casually.htmlParse.staticdata.RegexStaticData;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 图片处理类
 * @author 13545
 * @create-time 2017/10/30 21:38
 */
public class ImageUtil {

    private static BASE64Encoder encoder = new sun.misc.BASE64Encoder();
    private static BASE64Decoder decoder = new sun.misc.BASE64Decoder();
    private static Pattern pattern = Pattern.compile(RegexStaticData.imgPostfixRegex);

    /**
     * 将图片文件转换为二进制文件
     * @param imgPath 文件地址
     * @return
     */
    public static String getImageBinary(String imgPath) {
        Matcher matcher = pattern.matcher(imgPath);
        String postfix = "";
        while (matcher.find()){
            postfix = matcher.group().substring(1);
        }
        File f = new File(imgPath);
        BufferedImage bi;
        try {
            bi = ImageIO.read(f);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //经测试转换的图片是格式这里就什么格式，否则会失真
            ImageIO.write(bi, postfix, baos);
            byte[] bytes = baos.toByteArray();
            return encoder.encodeBuffer(bytes).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将base64格式转换为图片
     * @param base64String base64格式数据
     * @param imgPath 最终图片存放地址
     */
    public static void base64StringToImage(String base64String,String imgPath) {
        Matcher matcher = pattern.matcher(imgPath);
        String postfix = "";
        while (matcher.find()){
            postfix = matcher.group();
        }
        try {
            byte[] bytes1 = decoder.decodeBuffer(base64String);

            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            BufferedImage bi1 = ImageIO.read(bais);
            // 可以是jpg,png,gif格式
            File w2 = new File(imgPath);
            // 不管输出什么格式图片，此处不需改动
            ImageIO.write(bi1, postfix, w2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
