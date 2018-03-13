package demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by baozi on 12/03/2018.
 */
@RestController
@RefreshScope // 使用该注解的类，会在接到SpringCloud配置中心配置刷新的时候，自动将新的配置更新到该类对应的字段中。
public class HelloController {


    @Value("${dev.hello}")
    public String hello;

    @Value("${dev.hellos}")
    public String[] list;

    @RequestMapping("/hello")
    public String from() {
        return this.hello;
    }

    @RequestMapping("/hellos")
    public String hi() {
        StringBuilder str = new StringBuilder();
        for (String s : list) {
            str.append(s + "**");
        }
        return str.toString();
    }
}
