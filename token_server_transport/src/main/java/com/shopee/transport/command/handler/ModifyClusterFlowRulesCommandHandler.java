package com.shopee.transport.command.handler;

import com.shopee.transport.command.CommandHandler;
import com.shopee.transport.command.CommandRequest;
import com.shopee.transport.command.CommandResponse;
import com.shopee.transport.command.annotation.CommandMapping;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.util.StringUtils;

import java.net.URLDecoder;

/**
 * 修改集群的限流配置hanlder
 *
 * @author honggang.liu
 */
@CommandMapping(name = "cluster/server/modifyFlowRules", desc = "修改集群限流规则")
public class ModifyClusterFlowRulesCommandHandler implements CommandHandler<String> {
    /**
     * 处理给定的 命令请求。
     *
     * @param request 请求体
     * @return 处理结果
     */
    @Override
    public CommandResponse<String> handle(CommandRequest request) {
        String data = request.getParam("data");
        if (StringUtils.isEmpty(data)) {
            return CommandResponse.ofFailure(new IllegalArgumentException("empty data"));
        }
        try {
            data = URLDecoder.decode(data, "UTF-8");
            //RecordLog.info("[ModifyClusterFlowRulesCommandHandler] Receiving cluster flow rules for namespace <{}>: {}", namespace, data);

            //List<FlowRule> flowRules = JSONArray.parseArray(data, FlowRule.class);
            //ClusterFlowRuleManager.loadRules(namespace, flowRules);

            return CommandResponse.ofSuccess(SUCCESS);
        } catch (Exception e) {
            //RecordLog.warn("[ModifyClusterFlowRulesCommandHandler] Decode cluster flow rules error", e);
            return CommandResponse.ofFailure(e, "decode cluster flow rules error");
        }
    }


    /**
     * 成功msg
     */
    private static final String SUCCESS = "success";
}
