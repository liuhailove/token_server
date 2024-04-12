package com.shopee.transport.command;


/**
 * 命令中心的命令氢气响应
 *
 * @author honggang.liu
 */
public class CommandResponse<R> {

    /**
     * 是否成功
     */
    private final boolean success;

    /**
     * 操作结果
     */
    private final R result;

    /**
     * 抛出的异常
     */
    private final Throwable exception;

    private CommandResponse(R result) {
        this(result, true, null);
    }

    private CommandResponse(R result, boolean success, Throwable exception) {
        this.success = success;
        this.result = result;
        this.exception = exception;
    }

    /**
     * 构造一个成功的响应体
     *
     * @param result 结果
     * @return 一个成功的构造响应体
     */
    public static <T> CommandResponse<T> ofSuccess(T result) {
        return new CommandResponse<>(result);
    }

    public static <T> CommandResponse<T> ofFailure(Throwable ex) {
        return new CommandResponse<>(null, false, ex);
    }

    /**
     * 使用给定的消息构造失败返回
     *
     * @param ex     异常
     * @param result msg
     * @return
     */
    public static <T> CommandResponse<T> ofFailure(Throwable ex, T result) {
        return new CommandResponse<>(result, false, ex);
    }

    public boolean isSuccess() {
        return success;
    }

    public R getResult() {
        return result;
    }

    public Throwable getException() {
        return exception;
    }
}
