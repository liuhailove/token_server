package com.shopee.transport.command;

import com.shopee.token_server_core.spi.SpiLoader;
import com.shopee.transport.command.annotation.CommandMapping;
import com.shopee.transport.command.handler.InterceptingCommandHandler;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 提供并过滤通过 SPI 注册的命令处理程序。
 *
 * @author honggang.liu
 */
public class CommandHandlerProvider implements Iterable<CommandHandler> {

    private final SpiLoader<CommandHandler> spiLoader = SpiLoader.of(CommandHandler.class);

    /**
     * 获取有 CommandMapping 注解的全部handler
     *
     * @return handler列表
     */
    public Map<String, CommandHandler> namedHandlers() {
        Map<String, CommandHandler> map = new HashMap<>(8);
        List<CommandHandler> handlers = spiLoader.loadInstanceList();
        List<CommandHandlerInterceptor> commandHandlerInterceptors = SpiLoader.of(CommandHandlerInterceptor.class).loadInstanceListSorted();
        for (CommandHandler handler : handlers) {
            String name = parseCommandName(handler);
            if (StringUtils.isEmpty(name)) {
                continue;
            }
            if (!commandHandlerInterceptors.isEmpty()) {
                List<CommandHandlerInterceptor> interceptors = new ArrayList<>();
                for (CommandHandlerInterceptor commandHandlerInterceptor : commandHandlerInterceptors) {
                    if (commandHandlerInterceptor.shouldIntercept(name)) {
                        interceptors.add(commandHandlerInterceptor);
                    }
                }
                if (!interceptors.isEmpty()) {
                    handler = new InterceptingCommandHandler(handler, interceptors);
                }
            }
            map.put(name, handler);
        }
        return map;
    }

    /**
     * 解析命令名称
     *
     * @param handler handler
     * @return 命令名称
     */
    private String parseCommandName(CommandHandler handler) {
        CommandMapping commandMapping = handler.getClass().getAnnotation(CommandMapping.class);
        if (commandMapping != null) {
            return commandMapping.name();
        }
        return null;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<CommandHandler> iterator() {
        return spiLoader.loadInstanceList().iterator();
    }

    private static final CommandHandlerProvider INSTANCE = new CommandHandlerProvider();

    public static CommandHandlerProvider getInstance() {
        return INSTANCE;
    }
}
