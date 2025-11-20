package com.ensa.miniproject.aspects;

import com.ensa.miniproject.dto.ProjectDTO;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class ValidationAspect {

        private static final Logger logger = LoggerFactory.getLogger(ValidationAspect.class);

        @Around("execution(* com.ensa.miniproject.services.project.ProjectService.addProject()) && args(projectDTO)")
        public Object validateAndExecuteProjectDTO(ProceedingJoinPoint joinPoint, ProjectDTO projectDTO) throws Throwable {
            // Validate the projectDTO before proceeding
            if (projectDTO.dateDebut().isAfter(projectDTO.dateFin()
            )) {
                throw new IllegalArgumentException("La date de début doit être inférieure à la date de fin.");
            }

            // Log before the method execution
            logger.info("Validation passed. Proceeding with the addProject method.");

            // Proceed with the method execution
            Object result = joinPoint.proceed();

            // Log a success message after the method execution
            logger.info("addProject method executed successfully.");

            return result;
        }

}
