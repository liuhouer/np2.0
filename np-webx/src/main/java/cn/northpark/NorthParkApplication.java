package cn.northpark;

import cn.northpark.interceptor.AdminInterceptor;
import cn.northpark.interceptor.LoginInterceptor;
import cn.northpark.interceptor.UseCKInterceptor;
import cn.northpark.utils.JsonUtil;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.github.pagehelper.PageInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.sql.DataSource;
import java.net.InetAddress;
import java.util.*;

@SpringBootApplication
@MapperScan("cn.northpark.mapper")
@Slf4j
public class NorthParkApplication extends SpringBootServletInitializer implements WebMvcConfigurer, ErrorPageRegistrar {


    /**
     * mybatis设置
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        // 设置类型别名包
        factoryBean.setTypeAliasesPackage("cn.northpark.model");

        // 配置 PageHelper 插件
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql"); // 根据你的数据库类型设置，例如 mysql、postgresql、oracle
        properties.setProperty("reasonable", "true"); // 启用合理化
        properties.setProperty("supportMethodsArguments", "true"); // 支持通过 Mapper 接口参数传递分页参数
        properties.setProperty("params", "count=countSql"); // 分页参数
        pageInterceptor.setProperties(properties);

        factoryBean.setPlugins(new Interceptor[]{pageInterceptor});

        // 设置映射文件位置
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/**/*.xml"));

        return factoryBean.getObject();
    }

	/**
     * 使用 fastJson
     */
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        //1、定义一个convert转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        //2、添加fastjson的配置信息
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        //3、在convert中添加配置信息
        fastConverter.setFastJsonConfig(fastJsonConfig);
        //4、将convert添加到converters中
        HttpMessageConverter<?> converter = fastConverter;
        return new HttpMessageConverters(converter);
    }


    /**
     * tomcat在8.5 中 修改了加载jar的方式，8.5 版本会解析jar中MANIFEST.MF文件，
     * 当该文件包含class-path属性时，会把该属性对象值，解析成需要加载的jar给加载进来。
     * @return
     */
    @Bean
    public TomcatServletWebServerFactory tomcatFactory(){
        return new TomcatServletWebServerFactory(){
            @Override
            protected void postProcessContext(Context context) {
                ((StandardJarScanner) context.getJarScanner()).setScanManifest(false);
            }
        };
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(NorthParkApplication.class);
    }

    /**
     * 拦截器注册
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new UseCKInterceptor()).addPathPatterns("/**");
    }

    /**
     * 解决字体跨域加载
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowedHeaders("*");
    }

    /**
     * 指定500|404错误跳转
     * @param registry
     */
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/errors");
        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/building");

        registry.addErrorPages(errorPage500, errorPage404);
    }


    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the RequestMappingHandlerMapping provided by NorthPark:");

            RequestMappingHandlerMapping mapping = ctx.getBean(RequestMappingHandlerMapping.class);
            Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
                Map<String, String> map1 = new HashMap<String, String>();
                RequestMappingInfo info = m.getKey();
                HandlerMethod method = m.getValue();
                PatternsRequestCondition p = info.getPatternsCondition();
                for (String url : p.getPatterns()) {
                    map1.put("url", url);
                }
                log.info(JsonUtil.object2json(map1.get("url")));
            }


        };
    }

    public static void main(String[] args) throws Exception{

        ConfigurableApplicationContext application=SpringApplication.run(NorthParkApplication.class, args);

        Environment env = application.getEnvironment();
        log.info("\n----------------------------------------------------------\n\t" +
                        "Application 'NorthPark' is running! Access URLs:\n\t" +
                        "Local: \t\thttp://localhost:{}\n\t" +
                        "External: \thttp://{}:{}\n\t"+
                        "----------------------------------------------------------",
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"));

	}

}

