package com.dean4j.framework;

import com.dean4j.framework.helper.*;
import com.dean4j.framework.uitl.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加载相应的 Helper 类
 *
 * @author hunan
 * @since 1.0.0
 */
public final class HelperLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelperLoader.class);

    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };

        /**
         * 加载其static 模块
         */
        for (Class<?> cls : classList) {
            LOGGER.info("框架初始化" + cls);
            ClassUtil.loadClass(cls.getName(), true);
        }
    }

}
