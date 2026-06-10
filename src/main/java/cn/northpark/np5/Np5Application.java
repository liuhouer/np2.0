package cn.northpark.np5;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@MapperScan("cn.northpark.np5.mapper")
@Slf4j
public class Np5Application {
    public static void main(String[] args) throws UnknownHostException {
        ApplicationContext applicationContext = SpringApplication.run(Np5Application.class, args);
        Environment env = applicationContext.getBean(Environment.class);
        String port = env.getProperty("server.port", "8080");
        String host = InetAddress.getLocalHost().getHostAddress();
        
        log.info("\n----------------------------------------------------------\n\t" +
                "Application np5 is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + "/\n\t" +
                "External: \thttp://" + host + ":" + port + "/\n" +
                "----------------------------------------------------------");

        log.info("-------------------- Registered API Endpoints --------------------");
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        mapping.getHandlerMethods().forEach((key, value) -> {
            log.info("Patterns: {} ", key.getPatternValues());
        });
        log.info("------------------------------------------------------------------");
    }
}