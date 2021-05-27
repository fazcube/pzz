package pzz.common.system.api;

//import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author  PZJ
 * @create  2021/5/24 15:23
 * @email   wuchzh0@gmail.com
 * @desc    创建FeignClient
 **/
//@FeignClient(name = "sysBaseApi",url = "http://127.0.0.1:8888/shiro")
public interface SysBaseAPI {

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    @GetMapping("/sys/api/getUserByUsername")
    void getUserByUsername(@RequestParam("username") String username);
}
