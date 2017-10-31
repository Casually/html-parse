package cc.casually.htmlParse.kuangshikeji;

import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * 旷视科技静态参数
 */
public class StaticData {

    /**
     * api_key
     */
    public static final String API_KEY = "XqtiJYybaerD7jqbsnQnAxVnyxRQdnUg";

    /**
     * api_secret
     */
    public static final String API_SECRET = "6nW9Pi9gGyQJrpdHCffVbIhoMcmWnmRR";

    /**
     * outer_id
     */
    public static final String OUTER_ID = "www_casually_cc";

    /**
     * 人脸识别url
     */
    public static final String DETECT_URL = "https://api-cn.faceplusplus.com/facepp/v3/detect";

    /**
     * 人脸查找（在一用的人脸库中查找相识度高的图片一张或多张）
     */
    public static final String SEARCH_URL = "https://api-cn.faceplusplus.com/facepp/v3/search";

    /**
     * 创建人脸集合
     */
    public static final String CREATE_FACESET_URL = "https://api-cn.faceplusplus.com/facepp/v3/faceset/create";

    /**
     * 获取指定人脸集合信息
     */
    public static final String GET_FACESET_URL = "https://api-cn.faceplusplus.com/facepp/v3/faceset/getdetail";

    /**
     * 删除指定人脸集合信息
     */
    public static final String DELETE_FACESET_URL = "https://api-cn.faceplusplus.com/facepp/v3/faceset/delete";

    /**
     * 修改人脸集合信息
     */
    public static final String UPDATE_FACESET_URL = "https://api-cn.faceplusplus.com/facepp/v3/faceset/update";

    /**
     * 获取所有人脸集合信息
     */
    public static final String GET_FACESETS_URL = "https://api-cn.faceplusplus.com/facepp/v3/faceset/getfacesets";

    /**
     * 添加人脸信息
     */
    public static final String ADD_FACE_URL = "https://api-cn.faceplusplus.com/facepp/v3/faceset/addface";

    /**
     * 删除人脸信息
     */
    public static final String REMOVE_FACE_URL = " https://api-cn.faceplusplus.com/facepp/v3/faceset/removeface";

    /**
     * 人体检测
     */
    public static final String HUMANBODY_DETECT_URL = "https://api-cn.faceplusplus.com/humanbodypp/v1/detect";

    /**
     * 人形抠像
     */
    public static final String HUMANBODY_SEGMENT_URL = "https://api-cn.faceplusplus.com/humanbodypp/v1/segment";
}
