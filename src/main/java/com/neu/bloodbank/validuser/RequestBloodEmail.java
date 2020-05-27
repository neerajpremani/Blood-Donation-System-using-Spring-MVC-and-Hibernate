package com.neu.bloodbank.validuser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = RequestBloodEmailValidator.class)
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestBloodEmail {
	
	public String message() default "Donor Username doesn't exist!";
	
	public Class<?>[] groups() default{};
	
	public Class<? extends Payload>[] payload() default{};


}
