package com.shopee.transport.command;

/**
 * 命令处理
 *
 * @author honggang.liu
 */
public interface CommandHandler<R> {

    /**
     * 处理给定的 命令请求。
     *
     * @param request 请求体
     * @return 处理结果
     */
    CommandResponse<R> handle(CommandRequest request);
}
