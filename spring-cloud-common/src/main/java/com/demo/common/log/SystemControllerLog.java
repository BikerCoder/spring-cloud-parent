package com.demo.common.log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;



/**
 * 自定义   日志拦截controller
 * 
 * @author  lzy
 * @version  [版本号, 2017年5月5日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})    
@Retention(RetentionPolicy.RUNTIME)    
@Documented  
public @interface SystemControllerLog
{
    /**
     * 描述信息
     * <功能详细描述>
     * @return [参数说明]
     * 
     * @return String [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    String description()  default "";
    
}
