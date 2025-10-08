package com.example.test_task_exirom.validation

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ValidDateValidator::class])
annotation class ValidDate(
    val message: String = "invalid expiryDate: must be after today",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)