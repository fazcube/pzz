package org.pzz.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.pzz.common.vo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author  PZJ
 * @create  2021/4/30 16:01
 * @email   wuchzh0@gmail.com
 * @desc    全局异常捕获处理器
 **/
@RestControllerAdvice
@Slf4j
public class FazExceptionHandler {

    @ExceptionHandler({UnauthorizedException.class, AuthorizationException.class})
    public Result<?> handleAuthorizationException(AuthorizationException e){
        log.error(e.getMessage(), e);
        return Result.noauth("该角色无权限！");
    }
}
