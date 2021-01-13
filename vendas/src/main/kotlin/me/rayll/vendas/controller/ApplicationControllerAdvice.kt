package me.rayll.vendas.controller

import me.rayll.vendas.exception.PedidoNaoEncontradoException
import me.rayll.vendas.exception.RegraNegocioException
import me.rayll.vendas.rest.ApiErrors
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ApplicationControllerAdvice{

    @ExceptionHandler(value = [(RegraNegocioException::class)])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleRegraNegocioException(ex: RegraNegocioException): ApiErrors{
        var mensagemErro = ex.message ?: ""
        return ApiErrors(mensagemErro)
    }

    @ExceptionHandler(value = [(PedidoNaoEncontradoException::class)])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun pedidoNotFoundExcepetion(ex: PedidoNaoEncontradoException): ApiErrors{
        var messagemErro = ex.message ?: "Pedido não encontrado."
        return ApiErrors(messagemErro)
    }

    @ExceptionHandler(value = [(MethodArgumentNotValidException::class)])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMethodNotValidException(ex: MethodArgumentNotValidException): ApiErrors{
        var errorList = ex.bindingResult.allErrors.map { it.defaultMessage }
        return ApiErrors(errorList)
    }
}