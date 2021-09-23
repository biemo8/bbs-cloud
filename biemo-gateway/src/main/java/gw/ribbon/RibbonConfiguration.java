package gw.ribbon;

import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ribbon的配置
 *
 *
 * @Date 2019/8/13 21:19
 */
@Configuration
public class RibbonConfiguration {

    @Bean
    public IRule ribbonRule() {
         /*com.netflix.loadbalancer.RoundRobinRule：轮询规则
        com.netflix.loadbalancer.AvailabilityFilteringRule：根据服务是否死掉或者服务处于高并发来分配权重
        com.netflix.loadbalancer.WeightedResponseTimeRule：根据响应时间分配权重
        com.netflix.loadbalancer.RandomRule：随机规则
        com.netflix.loadbalancer.ReryRule：重试（先按照轮询规则获取服务，如果获取服务失败则在指定时间内进行重试)
        com.netflix.loadbalancer.ZoneAvoidanceRule:默认规则，复合判断Server所在区域的性能和Server的可用性选择服务器
        com.netflix.loadbalancer.BestAvailableRule:先过滤掉由于多次访问故障而处于断路器跳闸状态的服务，然后选择一个并发量最小的服务*/

        return new RoundRobinRule();
        //return new ZoneAvoidanceRule();
        //return new AvailabilityFilteringRule();
        //return new WeightedResponseTimeRule();
    }

}
