package com.demo.common.log;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.demo.common.exception.ParameterException;
import com.demo.common.utils.GsonUtil;
import com.demo.response.MessageResult;
import com.demo.response.Response;

/**
 * 日志切点类
 * 
 * @author lzy
 * @version [版本号, 2017年5月5日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@Aspect
@Component
@ConditionalOnProperty("spring.application.name")
public class SystemLogAspect {
	// 本地异常日志记录对象
	private static final ServerLogger logger = new ServerLogger(SystemLogAspect.class);

	// Controller层切点
	@Pointcut("@annotation(com.demo.common.log.SystemControllerLog)")
	public void controllerAspect() {

	}

	@Value("${spring.application.name}")
	private String project;

	/**
	 * 前置通知 用于拦截Controller层记录用户的操作
	 * 
	 * @param joinPoint
	 *            切点
	 */
	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) {
		try {
			if (null == RequestContextHolder.getRequestAttributes()) {
				return;
			}
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();

			String parentLogId = request.getHeader(LoggerConstants.HTTP_HEAD_PARENT_LOG_ID);
			
//			String tenant_org_id = request.getHeader(Constants.HTTP_HEAD_TENANT_ORG_ID);

			// 获取用户请求方法的参数并序列化为JSON格式字符串
			String params = "";
			if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
				for (int i = 0; i < joinPoint.getArgs().length; i++) {
					params += GsonUtil.toJson(joinPoint.getArgs()[i]) + ";";
				}
			}

			String logId = UUID.randomUUID().toString();
			// 读取session中的用户
			// 请求的IP
			String ip = request.getRemoteAddr();
			SystemLog log = new SystemLog();
			log.setLogId(logId);
			log.setLogClass(joinPoint.getTarget().getClass().getName());
			log.setMethod(joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()");
			log.setQuestIp(ip);
			log.setLogType(LoggerConstants.CONTROLL_LOG_TYPE);
			log.setDescription(getControllerMethodDescription(joinPoint));
			log.setCreatetime(new Date());
			log.setProject(project);
			log.setQuestParam(params);
			if (StringUtils.isNotBlank(parentLogId)) {
				log.setParentLogId(parentLogId);
			}
			String logJson = GsonUtil.toJson(log);

			logger.info(logJson);
		} catch (Exception e) {
			// 记录本地异常日志
			logger.error(e.toString(), e);
		}
	}

	/**
	 * 前置通知 用于拦截Controller层记录用户的操作
	 * 
	 * @param proceedingJoinPoint
	 *            切点
	 */
	@Around("controllerAspect()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Object result = null;
		try {
			result = proceedingJoinPoint.proceed();
		} catch (Exception e) {
			Response<String> resultDTO = new Response<String>();
			MessageResult messageResult = new MessageResult();
			if (e instanceof ParameterException) {
				ParameterException errorCodeException = (ParameterException) e;
				messageResult.setResultCode(errorCodeException.getCode());
				messageResult.setResultMsg(errorCodeException.getMessage());
				messageResult.setErrorParam(errorCodeException.getParaName());
			} else {
				messageResult.setResultCode("10003");
				messageResult.setResultMsg(LoggerConstants.SYSTEM_ERROR);
			}
			resultDTO.setServerResult(messageResult);
			logger.error(e.getMessage(),e);
			String params = "";
			if (proceedingJoinPoint.getArgs() != null && proceedingJoinPoint.getArgs().length > 0) {
				for (int i = 0; i < proceedingJoinPoint.getArgs().length; i++) {
					params += GsonUtil.toJson(proceedingJoinPoint.getArgs()[i]) + ";";
				}
			}
			/* ==========记录本地异常日志========== */
			logger.error("异常方法:{" + proceedingJoinPoint.getTarget().getClass().getName() +"." + proceedingJoinPoint.getSignature().getName()
					+ "}异常信息:{" + e.getMessage() + "}参数:{" + params + "}", e);

			return resultDTO;
		}
		return result;
    }

	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 * 
	 * @param joinPoint
	 *            切点
	 * @return 方法描述
	 * @throws Exception
	 */
	public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length && null != method.getAnnotation(SystemControllerLog.class)) {
					description = method.getAnnotation(SystemControllerLog.class).description();
					break;
				}
			}
		}
		return description;
	}
}
