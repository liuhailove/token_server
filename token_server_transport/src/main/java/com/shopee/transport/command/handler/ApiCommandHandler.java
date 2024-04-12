package com.shopee.transport.command.handler;

import com.shopee.transport.command.CommandHandler;
import com.shopee.transport.command.CommandHandlerProvider;
import com.shopee.transport.command.CommandRequest;
import com.shopee.transport.command.CommandResponse;
import com.shopee.transport.command.annotation.CommandMapping;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * 获取全部可用的handler
 *
 * @author honggang.liu
 */
@CommandMapping(name = "api", desc = "get all available command handlers")
public class ApiCommandHandler implements CommandHandler<String> {
    /**
     * 处理给定的 命令请求。
     *
     * @param request 请求体
     * @return 处理结果
     */
    @Override
    public CommandResponse<String> handle(CommandRequest request) {
        Map<String, CommandHandler> handlers = CommandHandlerProvider.getInstance().namedHandlers();
        JSONArray array = new JSONArray();
        if (handlers.isEmpty()) {
            return CommandResponse.ofSuccess(array.toJSONString());
        }
        for (CommandHandler handler : handlers.values()) {
            CommandMapping commandMapping = handler.getClass().getAnnotation(CommandMapping.class);
            if (commandMapping == null) {
                continue;
            }
            String api = commandMapping.name();
            String desc = commandMapping.desc();
            JSONObject obj = new JSONObject();
            obj.put("url", "/" + api);
            obj.put("desc", desc);
            array.add(obj);
        }
        return CommandResponse.ofSuccess(array.toJSONString());
    }
}
