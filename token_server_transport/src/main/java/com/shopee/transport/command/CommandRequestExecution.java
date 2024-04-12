package com.shopee.transport.command;

/**
 * 执行命令请求
 *
 * @author honggang.liu
 */
public interface CommandRequestExecution<R> {
    /**
     * 执行提供的命令请求并返回处理结果
     *
     * @param request 请求体
     * @return 执行结果
     */
    CommandResponse<R> execute(CommandRequest request);
}
