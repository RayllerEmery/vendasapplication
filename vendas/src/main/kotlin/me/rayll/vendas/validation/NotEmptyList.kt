package me.rayll.vendas.validation

import me.rayll.vendas.validation.constraintvalidation.NotEmptyListValidation
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.validation.Constraint
import kotlin.reflect.KClass
import javax.validation.Payload

@Retention(RetentionPolicy.RUNTIME)
@Target(AnnotationTarget.FIELD)
@Constraint(validatedBy = [NotEmptyListValidation::class])
annotation class NotEmptyList(val message: String = "A lista não pode ser vazia.", val groups: Array<KClass<*>> = [], val payload: Array<KClass<*>> = [])