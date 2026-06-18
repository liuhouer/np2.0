package cn.northpark.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * @author Bruce
 * spring手动获取bean的工具类
 */
@Component
public class SpringContextUtils implements ApplicationContextAware {//extends ApplicationObjectSupport{

    private static volatile ApplicationContext applicationContext = null;


    @Override
    public void setApplicationContext(ApplicationContext appContext) throws BeansException {
        if (SpringContextUtils.applicationContext == null) {
            SpringContextUtils.applicationContext = appContext;
            System.out.println(
                    "========ApplicationContext配置成功,在普通类可以通过调用ToolSpring.getAppContext()获取applicationContext对象,applicationContext="
                            + applicationContext + "========");
        }
    }


    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 根据类型获取 Bean（推荐写法）
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }
    /**
     * 根据名称 + 类型获取 Bean（更安全，避免同类型多个 Bean 的情况）
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}
