package com.shopee.transport.command;

/**
 * 拦截指定命令，并可以使用SPI进行扩展。
 *
 * @author honggang.liu
 */
public interface CommandHandlerInterceptor<R> {

    /**
     * 是否拦截指定命令
     *
     * @param commandName 命令名称
     * @return true 拦截, false 跳过
     */
    boolean shouldIntercept(String commandName);

    /**
     * 拦截给定的请求，给出命令响应
     *
     * @param request   请求
     * @param execution 执行异常
     * @return
     */
    CommandResponse<R> intercept(CommandRequest request, CommandRequestExecution<R> execution);
}
