package com.shopee.transport.command.handler;

import com.shopee.transport.command.*;
import org.springframework.util.Assert;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * 拦截指定的命令处理程序
 *
 * @author honggang.liu
 */
public class InterceptingCommandHandler<R> implements CommandHandler<R> {

    /**
     * 代理
     */
    private final CommandHandler<R> delegate;

    /**
     * 命令拦截器
     */
    private final List<CommandHandlerInterceptor<R>> commandHandlerInterceptors;


    public InterceptingCommandHandler(CommandHandler<R> delegate, List<CommandHandlerInterceptor<R>> commandHandlerInterceptors) {
        Assert.notNull(delegate, "delegate cannot be null");
        Assert.notNull(commandHandlerInterceptors, "commandHandlerInterceptors cannot be null");
        this.delegate = delegate;
        this.commandHandlerInterceptors = commandHandlerInterceptors;
    }

    /**
     * 处理给定的 命令请求。
     *
     * @param request 请求体
     * @return 处理结果
     */
    @Override
    public CommandResponse<R> handle(CommandRequest request) {
        return new InterceptingRequestExecution<>(commandHandlerInterceptors.iterator(), delegate::handle).execute(request);
    }

    private static class InterceptingRequestExecution<R> implements CommandRequestExecution<R> {

        /**
         * 迭代器
         */
        private final Iterator<CommandHandlerInterceptor<R>> iterator;
        /**
         * 命令响应Func
         */
        private final Function<CommandRequest, CommandResponse<R>> commandResponseFunction;

        public InterceptingRequestExecution(Iterator<CommandHandlerInterceptor<R>> iterator, Function<CommandRequest, CommandResponse<R>> commandResponseFunction) {
            this.iterator = iterator;
            this.commandResponseFunction = commandResponseFunction;
        }

        /**
         * 执行提供的命令请求并返回处理结果
         *
         * @param request 请求体
         * @return 执行结果
         */
        @Override
        public CommandResponse<R> execute(CommandRequest request) {
            if (this.iterator.hasNext()) {
                CommandHandlerInterceptor<R> nextInterceptor = this.iterator.next();
                return nextInterceptor.intercept(request, this);
            }
            return commandResponseFunction.apply(request);
        }
    }
}
