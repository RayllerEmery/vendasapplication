package me.rayll.vendas.validation.constraintvalidation

import javax.validation.ConstraintValidator
import me.rayll.vendas.validation.NotEmptyList
import javax.validation.ConstraintValidatorContext

class NotEmptyListValidation : ConstraintValidator<NotEmptyList?, List<*>?> {
    override fun initialize(constraintAnnotation: NotEmptyList?) {}
    override fun isValid(list: List<*>?, constraintValidatorContext: ConstraintValidatorContext): Boolean {
        return list != null && !list.isEmpty()
    }
}