package com.fin.best.bestfin.api.component.error

import com.fin.best.bestfin.api.component.model.ConditionalMessage
import com.fin.best.bestfin.api.component.model.RequestBuffer
import com.fin.best.bestfin.api.component.model.ResponseHandler
import com.fin.best.bestfin.api.component.constants.AppConst
import com.fin.best.bestfin.api.component.error.exception.ApiException
import com.fin.best.bestfin.api.component.error.exception.AuthenticateException
import com.fin.best.bestfin.api.component.error.exception.ParameterException
import com.fin.best.bestfin.api.component.utils.LogParser
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.ServletRequestBindingException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.multipart.MultipartException
import org.springframework.web.multipart.support.MissingServletRequestPartException
import javax.servlet.http.HttpServletRequest
import javax.validation.UnexpectedTypeException

@ControllerAdvice
class ExceptionHandleController
@Autowired constructor(
        private val requestBuffer: RequestBuffer
) {

    private val logger: Logger = LoggerFactory.getLogger(ExceptionHandler::class.java)
    private val moduleName: String = "EXC"

    private enum class LogLevel { WARN, ERROR }

    private fun traceLog(logLevel: LogLevel, elapsed: Long, remoteIp: String, reason: ConditionalMessage, exception: Exception, ignore: Boolean = false) = if (logLevel == LogLevel.WARN) {
        if (!ignore) logger.warn("{}", exception)
        logger.warn(LogParser.makeLog(4,
                AppConst.Log.ApplicationName,
                moduleName,
                remoteIp,
                AppConst.Log.Request,
                AppConst.Log.Elapsed, elapsed,
                AppConst.Log.ErrorCode, reason.code ?: 999,
                AppConst.Log.ErrorMessage, reason.message ?: exception.message ?: ""
        ))
    } else {
        if (!ignore) logger.error("{}", exception)
        logger.error(LogParser.makeLog(4,
                AppConst.Log.ApplicationName,
                moduleName,
                remoteIp,
                AppConst.Log.Response,
                AppConst.Log.Elapsed, elapsed,
                AppConst.Log.ErrorCode, reason.code ?: 999,
                AppConst.Log.ErrorMessage, reason.message ?: exception.message ?: ""
        ))
    }

    @ExceptionHandler(value = [
        MultipartException::class,
        MissingServletRequestParameterException::class,
        MissingServletRequestPartException::class,
        MethodArgumentTypeMismatchException::class,
        ServletRequestBindingException::class,
        HttpRequestMethodNotSupportedException::class,
        HttpMessageNotReadableException::class,
        HttpMediaTypeNotSupportedException::class
    ])
    @ResponseBody
    fun badRequestException(exception: Exception, request: HttpServletRequest): ResponseHandler<ConditionalMessage> {
        val reason = ConditionalMessage(code = HttpStatus.BAD_REQUEST.value(), message = exception.message
                ?: HttpStatus.BAD_REQUEST.name, result = null)
        traceLog(logLevel = LogLevel.WARN,
                elapsed = System.currentTimeMillis() - requestBuffer.incomingTime,
                remoteIp = requestBuffer.remoteIp ?: "0.0.0.0",
                exception = exception,
                reason = reason)
        return ResponseHandler(HttpStatus.BAD_REQUEST.value(), reason)
    }

    @ExceptionHandler(value = [
        ParameterException::class,
        MethodArgumentNotValidException::class,
        UnexpectedTypeException::class
    ])
    @ResponseBody
    fun parameterException(exception: Exception, request: HttpServletRequest): ResponseHandler<ConditionalMessage> {
        val reason = ConditionalMessage(code = HttpStatus.NOT_ACCEPTABLE.value(),
                message = if (exception is MethodArgumentNotValidException) ("[" + exception.bindingResult.fieldErrors[0].field + "]" + exception.bindingResult.fieldErrors[0].defaultMessage)
                else exception.message ?: HttpStatus.NOT_ACCEPTABLE.name, result = null)
        traceLog(logLevel = LogLevel.WARN,
                elapsed = System.currentTimeMillis() - requestBuffer.incomingTime,
                remoteIp = requestBuffer.remoteIp ?: "0.0.0.0",
                exception = exception,
                reason = reason)
        return ResponseHandler(HttpStatus.NOT_ACCEPTABLE.value(), reason)
    }

    @ExceptionHandler(value = [
        AuthenticateException::class,
        AuthenticationException::class
    ])
    @ResponseBody
    fun authenticateException(exception: Exception, request: HttpServletRequest): ResponseHandler<ConditionalMessage> {
        val reason = ConditionalMessage(code = HttpStatus.UNAUTHORIZED.value(), message = exception.message
                ?: HttpStatus.UNAUTHORIZED.name, result = null)
        traceLog(logLevel = LogLevel.WARN,
                elapsed = System.currentTimeMillis() - requestBuffer.incomingTime,
                remoteIp = requestBuffer.remoteIp ?: "0.0.0.0",
                exception = exception,
                reason = reason,
                ignore = true)
        return ResponseHandler(HttpStatus.UNAUTHORIZED.value(), reason)
    }

    @ExceptionHandler(value = [
        AccessDeniedException::class
    ])
    @ResponseBody
    fun accessDeniedException(exception: Exception, request: HttpServletRequest): ResponseHandler<ConditionalMessage> {
        val reason = ConditionalMessage(code = HttpStatus.FORBIDDEN.value(), message = exception.message
                ?: HttpStatus.FORBIDDEN.name, result = null)
        traceLog(logLevel = LogLevel.WARN,
                elapsed = System.currentTimeMillis() - requestBuffer.incomingTime,
                remoteIp = requestBuffer.remoteIp ?: "0.0.0.0",
                exception = exception,
                reason = reason)
        return ResponseHandler(HttpStatus.FORBIDDEN.value(), reason)
    }

    @ExceptionHandler(value = [
        ApiException::class
    ])
    @ResponseBody
    fun apiException(apiException: ApiException, request: HttpServletRequest): ResponseHandler<ConditionalMessage> {
        val reason = ConditionalMessage(
                code = apiException.errorCode,
                message = apiException.message,
                result = null)
        traceLog(logLevel = LogLevel.WARN,
                elapsed = System.currentTimeMillis() - requestBuffer.incomingTime,
                remoteIp = requestBuffer.remoteIp ?: "0.0.0.0",
                exception = apiException,
                reason = reason)
        return ResponseHandler(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.value(),
                AppConst.Response.DefaultFailedMessage, reason)
    }

    @ExceptionHandler(value = [Exception::class])
    @ResponseBody
    fun unhandledException(exception: Exception, request: HttpServletRequest): ResponseHandler<ConditionalMessage> {
        val reason = ConditionalMessage(code = HttpStatus.INTERNAL_SERVER_ERROR.value(), message = exception.message
                ?: HttpStatus.INTERNAL_SERVER_ERROR.name, result = null)
        traceLog(logLevel = LogLevel.ERROR,
                elapsed = System.currentTimeMillis() - requestBuffer.incomingTime,
                remoteIp = requestBuffer.remoteIp ?: "0.0.0.0",
                exception = exception,
                reason = reason)
        return ResponseHandler(HttpStatus.INTERNAL_SERVER_ERROR.value(), reason)
    }

}