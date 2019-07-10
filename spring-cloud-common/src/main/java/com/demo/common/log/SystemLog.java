package com.demo.common.log;

import java.util.Date;

/**
 * 系统日志实体类
 * 
 * @author  lzy
 * @version  [版本号, 2017年5月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SystemLog
{
    /**
     * 日志ID
     */
    private String logId;
    
    /**
     * 日志工程名
     */
    private String project;

    /**
     * 日志类
     */
    private String LogClass;
    
    /**
     * 异常消息
     */
    private String excepMessage;
    
    /**
     * 日志出现的方法
     */
    private String method;
    
    /**
     * 日志记录时间
     */
    private Date createtime;
    /**
     * 描述
     */
    private String description;
    
    /**
     * 请求IP
     */
    private String questIp;

    /**
     * 请求参数
     */
    private String questParam;
    
    /**
     * 父日志ID
     */
    private String parentLogId;

    public String getParentLogId()
    {
        return parentLogId;
    }

    public void setParentLogId(String parentLogId)
    {
        this.parentLogId = parentLogId;
    }

    /**
     * 1:操作日志,2:异常日志,3:自定义日志类型
     * 见：LoggerConstants.CONTROLL_LOG_TYPE 
     * LoggerConstants.SERVICE_LOG_TYPE 
     * LoggerConstants.CUSTOM_LOG_TYPE 
     */
    private String logType;
    
    public String getLogType()
    {
        return logType;
    }

    public void setLogType(String logType)
    {
        this.logType = logType;
    }

    public String getLogId()
    {
        return logId;
    }

    public void setLogId(String logId)
    {
        this.logId = logId;
    }


    public String getProject()
    {
        return project;
    }

    public void setProject(String project)
    {
        this.project = project;
    }

    public String getExcepMessage()
    {
        return excepMessage;
    }

    public void setExcepMessage(String excepMessage)
    {
        this.excepMessage = excepMessage;
    }

    public String getLogClass()
    {
        return LogClass;
    }

    public void setLogClass(String logClass)
    {
        LogClass = logClass;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getQuestIp()
    {
        return questIp;
    }

    public void setQuestIp(String questIp)
    {
        this.questIp = questIp;
    }

    public String getQuestParam()
    {
        return questParam;
    }

    public void setQuestParam(String questParam)
    {
        this.questParam = questParam;
    }
    
    public Date getCreatetime()
    {
        return createtime;
    }

    public void setCreatetime(Date createtime)
    {
        this.createtime = createtime;
    }
    
    
}
