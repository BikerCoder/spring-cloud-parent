package com.demo.common.log;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.common.utils.GsonUtil;


/**
 * 
 * 日志工具类
 * 
 * @author lzy
 * @version [版本号, 2017年5月17日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public final class ServerLogger
{
    private Logger logger;
    
    public ServerLogger(Class<?> clazz)
    {
        this.logger = LoggerFactory.getLogger(clazz);
    }
    
    public void debug(String message, Throwable t)
    {
        logger.debug(message, t);
    }
    
    public void debug(String message, String projectName, Throwable t)
    {
		message = this.messageLog(message, "1", projectName, null, t);
        logger.debug(message, t);
    }
    
    public void debug( String message, String projectName)
    {
		message = this.messageLog(message, "1", projectName, null, null);
        
        logger.debug(message);
    }
    
    public void debug( String message, String projectName,String parentLogId)
    {
		message = this.messageLog(message, "1", projectName, parentLogId, null);
        
        logger.debug(message);
    }
    
    public void debug(String message)
    {
        logger.debug(message);
    }
    
    public void error(String message, Throwable t)
    {
        logger.error(message, t);
    }
    
    public void error(String message)
    {
        logger.error(message);
    }
    
    public void error( String message, String projectName, Throwable t)
    {
		message = this.messageLog(message, "2", projectName, null, t);
        logger.error(message, t);
    }
    
    public void error( String message, String projectName, String parentLogId, Throwable t)
    {
		message = this.messageLog(message, "2", projectName, parentLogId, t);
        logger.error(message, t);
    }
    
    public void error( String message, String projectName)
    {
		message = this.messageLog(message, "2", projectName, null, null);
        
        logger.error(message);
    }
    
    public void info(String message, Throwable t)
    {
        logger.info(message, t);
    }
    
    public void info(String message)
    {
        logger.info(message);
    }
    
    public void info( String message, String projectName, Throwable t)
    {
		message = this.messageLog(message, "1", projectName, null, t);
        logger.info(message, t);
    }
    
    public void info(String message, String projectName)
    {
		message = this.messageLog(message, "1", projectName, null, null);
        
        logger.info(message);
    }
    
    public void info( String message, String projectName,String parentLogId)
    {
		message = this.messageLog(message, "1", projectName, parentLogId, null);
        
        logger.info(message);
    }
    
    public void warn(String message, Throwable t)
    {
        logger.warn(message, t);
    }
    
    public void warn(String message)
    {
        logger.warn(message);
    }
    
    public void warn( String message, String projectName, Throwable t)
    {
		message = this.messageLog(message, "1", projectName, null, t);
        logger.error(message, t);
    }
    
    public void warn( String message, String projectName)
    {
		message = this.messageLog(message, "1", projectName, null, null);
        
        logger.error(message);
    }
    
    public void warn( String message, String projectName,String parentLogId)
    {
		message = this.messageLog(message, "1", projectName, parentLogId, null);
        
        logger.error(message);
    }
    
    public boolean isDebugEnabled()
    {
        return logger.isDebugEnabled();
    }
    
    private String messageLog(String message, String logType, String project, String parentLogId, Throwable t)
    {
        SystemLog log = new SystemLog();
        /*** 获取输出信息的代码的位置 ***/
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        String method = stacks[2].getClassName() + "." + stacks[2].getMethodName() + "(" + stacks[2].getLineNumber()
            + ")";
        log.setMethod(method);
        log.setCreatetime(new Date());
        log.setLogId(UUID.randomUUID().toString());
        log.setLogClass(logger.getName());
        log.setLogType(logType);
        log.setProject(project);
        log.setDescription(message);
        if(StringUtils.isNotBlank(parentLogId))
        {
            log.setParentLogId(parentLogId);
        }
        if (null == t)
        {
            log.setExcepMessage(message);
        }
        else
        {
            log.setExcepMessage(t.getMessage());
        }
        	
        
        return GsonUtil.toJson(message);
    }
}
