package com.demo.response;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;


/**
 * Server的返回結果
 * 
 * @author lzy
 */
public class MessageResult implements Serializable
{
    private static final long serialVersionUID = -7556295999658379023L;
    
    /**
     * 结果码
     */
    @ApiModelProperty(value = "消息码")
    private String resultCode;
    
    /**
     * 结果消息
     */
    @ApiModelProperty(value = "结果信息")
    private String resultMsg;
    
    /**
     * 内部错误消息
     */
    @ApiModelProperty(value = "内部错误消息")
    private String internalMsg;
    
    /**
     * 错误的参数值，只有校验参数异常的情况下填充此值
     */
    private String errorParam;
    
    public MessageResult()
    {
        
    }
    
    public MessageResult(String resultCode)
    {
        this(resultCode, "Success");
    }
    
    public MessageResult(String resultCode, String resultMsg)
    {
        super();
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }
    
    
    public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getInternalMsg() {
		return internalMsg;
	}

	public void setInternalMsg(String internalMsg) {
		this.internalMsg = internalMsg;
	}

	public String getResultCode()
    {
        return resultCode;
    }
    
    public void setResultCode(String resultCode)
    {
        this.resultCode = resultCode;
    }

	public String getErrorParam() {
		return errorParam;
	}

	public void setErrorParam(String errorParam) {
		this.errorParam = errorParam;
	}
    
}
