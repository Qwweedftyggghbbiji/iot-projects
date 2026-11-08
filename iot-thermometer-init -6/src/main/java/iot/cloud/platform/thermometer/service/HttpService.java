package iot.cloud.platform.thermometer.service;

import iot.cloud.platform.thermometer.vo.DeviceMsgVo;
import iot.cloud.platform.thermometer.vo.DeviceRegisterVo;
import iot.cloud.platform.thermometer.vo.ResMsg;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.Map;

public interface HttpService {

  Retrofit RETROFIT = new Retrofit.Builder()
          .addConverterFactory(JacksonConverterFactory.create())
          .baseUrl("http://localhost:8098")
          .build();

  static HttpService getInstance() {
    return RETROFIT.create(HttpService.class);
  }

  /**
   * 调用物联网云平台 获取用户令牌 API
   * @param userId 用户ID
   * @param userSecret 用户密钥
   * @return
   */
  @GET("/token")
  Call<ResMsg> getUserToken(@Query("user_id") String userId,@Query("secret") String userSecret);

  /**
   * 调用物联网云平台 注册设备 API
   * @param token 用户令牌
   * @param dev 设备信息
   * @return
   */
  @POST("/device_register")
  Call<ResMsg> registerDevice(@Query("token") String token,@Body DeviceRegisterVo dev);

  /**
   * 调用物联网云平台 注册设备 API (兼容实验步骤)
   * @param token 用户令牌
   * @param device 设备信息
   * @return
   */
  @POST("/device_register")
  Call<ResMsg> registerDevice(@Query("token") String token, @Body Map<String, String> device);

  /**
   * 调用物联网云平台 新增设备消息 API
   * @param iotId 物联网云平台ID
   * @param secret 设备密钥
   * @param msg 消息
   * @return
   */
  @POST("/devicemsg_add")
  Call<ResMsg> sendDeviceMsg(@Query("iotId") String iotId, @Query("secret") String secret, @Body DeviceMsgVo msg);

  /**
   * 调用物联网云平台 新增设备消息 API (兼容实验步骤)
   * @param iotId 物联网云平台ID
   * @param secret 设备密钥
   * @param deviceMsg 消息
   * @return
   */
  @POST("/devicemsg_add")
  Call<ResMsg> sendDeviceMsg(@Query("iotId") String iotId, @Query("secret") String secret, @Body Map<String, Object> deviceMsg);
}
