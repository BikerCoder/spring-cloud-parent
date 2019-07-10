package com.demo.common.log;

public interface LoggerConstants
{
      
    /**
     * 操作日志
     */
    static final String CONTROLL_LOG_TYPE = "1";
    
    /**
     * 异常日志
     */
    static final String SERVICE_LOG_TYPE = "2";
    
    /**
     * 自定义日志的类型
     */
    static final String CUSTOM_LOG_TYPE = "3";
    
    /**
     * 系统父日志ID标致
     */
     static final String HTTP_HEAD_PARENT_LOG_ID = "Parent-LogId";
     
     /**
      * 数据库错误
      */
     static final String DB_EXCEPTION_ERROR = "数据库操作错误";
     
     static final String SYSTEM_ERROR = "系统未知错误，请检查服务!";
}
