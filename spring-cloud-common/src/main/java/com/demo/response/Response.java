package com.demo.response;

import java.io.Serializable;
import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * 接口返回抽象类
 * 
 * @author lzy
 */
public class Response<T> implements Serializable
{
    private static final long serialVersionUID = 6766428592214605847L;
    
    /**
     * 返回实体类
     */
    @ApiModelProperty(value = "返回实体对象")
    private T responseEntity;
    
    /**
     * 分页实体对角
     */
    @ApiModelProperty(value = "返回分页结果集")
    private PageInfo<T> pageInfo;
    
    /**
     * 接口返回結果码
     */
    @ApiModelProperty(value = "返回状态信息")
    private MessageResult serverResult;
    
    public Response()
    {
        this.serverResult = getServerResult();
    }
    
    public Response(T t)
    {
        this.responseEntity = t;
        this.serverResult = getServerResult();
    }
    
    public Response(List<T> list)
    {
        if (list instanceof Page)
        {
            this.pageInfo = new PageInfo<T>(list,((Page<T>) list).getPages());
        }
        else
        {
            this.pageInfo = new PageInfo<T>();
            this.pageInfo.setList(list);
            this.pageInfo.setTotal(null == list ? 0 : list.size());
        }
        this.serverResult = getServerResult();
    }
    
    public Response(String resultCode,String resultMessage,String internalMessage)
    {
        this.serverResult = new MessageResult();
        serverResult.setResultCode(resultCode);
        serverResult.setResultMsg(resultMessage);
        serverResult.setInternalMsg(internalMessage);
    }
    
    public Response(String resultCode,String resultMessage)
    {
        this(resultCode,resultMessage,resultMessage);
    }
    
    public MessageResult getServerResult()
    {
        /**
         * 默认为成功
         */
        if (serverResult == null)
        {
            serverResult = new MessageResult();
			serverResult.setResultCode("200");
            serverResult.setResultMsg("操作成功");
        }
        return serverResult;
    }
    
    public void setServerResult(MessageResult serverResult)
    {
        this.serverResult = serverResult;
    }
    
    public T getResponseEntity()
    {
        return responseEntity;
    }
    
    public void setResponseEntity(T responseEntity)
    {
        this.responseEntity = responseEntity;
    }
    
    public PageInfo<T> getPageInfo()
    {
        return pageInfo;
    }
    
    public void setPageInfo(PageInfo<T> pageInfo)
    {
        this.pageInfo = pageInfo;
    }
}
