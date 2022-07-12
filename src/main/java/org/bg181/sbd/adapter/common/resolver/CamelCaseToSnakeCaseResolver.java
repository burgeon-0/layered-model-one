package org.bg181.sbd.adapter.common.resolver;

import lombok.extern.slf4j.Slf4j;
import org.bg181.sbd.core.exception.ErrorCode;
import org.bg181.sbd.core.exception.ParamException;
import org.bg181.sbd.infra.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.annotation.ValidationAnnotationUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.*;

/**
 * 改造自解析ModelAttribute的ModelAttributeMethodProcessor
 *
 * @author Sam Lu
 * @date 2021/6/2
 * @see org.springframework.web.method.annotation.ModelAttributeMethodProcessor
 */
@Slf4j
public class CamelCaseToSnakeCaseResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CamelCaseToSnakeCase.class);
    }

    /**
     * Resolve the argument from the model or if not found instantiate it with
     * its default if it is available. The model attribute is then populated
     * with request values via data binding and optionally validated
     * if {@code @java.validation.Valid} is present on the argument.
     *
     * @throws BindException if data binding and validation result in an error
     * and the next method parameter is not of type {@link Errors}
     * @throws Exception if WebDataBinder initialization fails
     */
    @Override
    @Nullable
    public final Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer,
                                        NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {

        Assert.state(mavContainer != null, "CamelCaseToSnakeCaseResolver requires ModelAndViewContainer");
        Assert.state(binderFactory != null, "CamelCaseToSnakeCaseResolver requires WebDataBinderFactory");

        String name = ModelFactory.getNameForParameter(parameter);
        Object attribute = null;
        BindingResult bindingResult = null;

        if (mavContainer.containsAttribute(name)) {
            attribute = mavContainer.getModel().get(name);
        } else {
            // Create attribute instance
            try {
                attribute = createAttribute(name, parameter, binderFactory, webRequest);
            } catch (BindException ex) {
                if (isBindExceptionRequired(parameter)) {
                    // No BindingResult parameter -> fail with BindException
                    throw ex;
                }
                // Otherwise, expose null/empty value and associated BindingResult
                if (parameter.getParameterType() == Optional.class) {
                    attribute = Optional.empty();
                } else {
                    attribute = ex.getTarget();
                }
                bindingResult = ex.getBindingResult();
            }
        }

        if (bindingResult == null) {
            // Bean property binding and validation;
            // skipped in case of binding failure on construction.
            WebDataBinder binder = binderFactory.createBinder(webRequest, attribute, name);
            if (binder.getTarget() != null) {
                validateIfApplicable(binder, parameter);
                if (binder.getBindingResult().hasErrors() && isBindExceptionRequired(binder, parameter)) {
                    throw new BindException(binder.getBindingResult());
                }
            }
            // Value type adaptation, also covering java.util.Optional
            if (!parameter.getParameterType().isInstance(attribute)) {
                attribute = binder.convertIfNecessary(binder.getTarget(), parameter.getParameterType(), parameter);
            }
            bindingResult = binder.getBindingResult();
        }

        // Add resolved attribute and BindingResult at the end of the model
        Map<String, Object> bindingResultModel = bindingResult.getModel();
        mavContainer.removeAttributes(bindingResultModel);
        mavContainer.addAllAttributes(bindingResultModel);

        return attribute;
    }

    /**
     * Extension point to create the model attribute if not found in the model,
     * with subsequent parameter binding through bean properties (unless suppressed).
     * <p>The default implementation typically uses the unique public no-arg constructor
     * if available but also handles a "primary constructor" approach for data classes:
     * It understands the JavaBeans {@code ConstructorProperties} annotation as well as
     * runtime-retained parameter names in the bytecode, associating request parameters
     * with constructor arguments by name. If no such constructor is found, the default
     * constructor will be used (even if not public), assuming subsequent bean property
     * bindings through setter methods.
     *
     * @param attributeName the name of the attribute (never {@code null})
     * @param parameter the method parameter declaration
     * @param binderFactory for creating WebDataBinder instance
     * @param webRequest the current request
     * @return the created model attribute (never {@code null})
     * @throws BindException in case of constructor argument binding failure
     * @throws Exception in case of constructor invocation failure
     * @see #constructAttribute(Constructor, String, MethodParameter, WebDataBinderFactory, NativeWebRequest)
     * @see BeanUtils#findPrimaryConstructor(Class)
     */
    protected Object createAttribute(String attributeName, MethodParameter parameter,
                                     WebDataBinderFactory binderFactory, NativeWebRequest webRequest) throws Exception {

        MethodParameter nestedParameter = parameter.nestedIfOptional();
        Class<?> clazz = nestedParameter.getNestedParameterType();

        Constructor<?> ctor = BeanUtils.getResolvableConstructor(clazz);
        Object attribute = constructAttribute(ctor, attributeName, parameter, binderFactory, webRequest);
        if (parameter != nestedParameter) {
            attribute = Optional.of(attribute);
        }
        return attribute;
    }

    /**
     * Construct a new attribute instance with the given constructor.
     * <p>Called from
     * {@link #createAttribute(String, MethodParameter, WebDataBinderFactory, NativeWebRequest)}
     * after constructor resolution.
     *
     * @param ctor the constructor to use
     * @param attributeName the name of the attribute (never {@code null})
     * @param binderFactory for creating WebDataBinder instance
     * @param webRequest the current request
     * @return the created model attribute (never {@code null})
     * @throws BindException in case of constructor argument binding failure
     * @throws Exception in case of constructor invocation failure
     * @since 5.1
     */
    @SuppressWarnings("serial")
    protected Object constructAttribute(Constructor<?> ctor, String attributeName, MethodParameter parameter,
                                        WebDataBinderFactory binderFactory, NativeWebRequest webRequest) throws Exception {
        Object object = BeanUtils.instantiateClass(ctor);
        BeanWrapper src = new BeanWrapperImpl(object);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> paramsSet = new HashSet<>(pds.length);
        for (PropertyDescriptor pd : pds) {
            paramsSet.add(pd.getName());
        }

        Iterator<String> paramNames = webRequest.getParameterNames();
        while (paramNames.hasNext()) {
            String paramName = paramNames.next();
            Object paramVal = webRequest.getParameter(paramName);
            String newParamName = StringUtils.snakeCaseToCamelCase(paramName);
            if (paramsSet.contains(newParamName)) {
                try {
                    src.setPropertyValue(newParamName, paramVal);
                } catch (Exception e) {
                    throw new ParamException(ErrorCode.PARAM_INVALID,
                            String.format("The Param [%s] Is Invalid", paramName));
                }
            }
        }
        return object;
    }

    /**
     * Validate the model attribute if applicable.
     * <p>The default implementation checks for {@code @javax.validation.Valid},
     * Spring's {@link org.springframework.validation.annotation.Validated},
     * and custom annotations whose name starts with "Valid".
     *
     * @param binder the DataBinder to be used
     * @param parameter the method parameter declaration
     * @see WebDataBinder#validate(Object...)
     * @see SmartValidator#validate(Object, Errors, Object...)
     */
    protected void validateIfApplicable(WebDataBinder binder, MethodParameter parameter) {
        for (Annotation ann : parameter.getParameterAnnotations()) {
            Object[] validationHints = ValidationAnnotationUtils.determineValidationHints(ann);
            if (validationHints != null) {
                binder.validate(validationHints);
                break;
            }
        }
    }

    /**
     * Whether to raise a fatal bind exception on validation errors.
     * <p>The default implementation delegates to {@link #isBindExceptionRequired(MethodParameter)}.
     *
     * @param binder the data binder used to perform data binding
     * @param parameter the method parameter declaration
     * @return {@code true} if the next method parameter is not of type {@link Errors}
     * @see #isBindExceptionRequired(MethodParameter)
     */
    protected boolean isBindExceptionRequired(WebDataBinder binder, MethodParameter parameter) {
        return isBindExceptionRequired(parameter);
    }

    /**
     * Whether to raise a fatal bind exception on validation errors.
     *
     * @param parameter the method parameter declaration
     * @return {@code true} if the next method parameter is not of type {@link Errors}
     * @since 5.0
     */
    protected boolean isBindExceptionRequired(MethodParameter parameter) {
        int i = parameter.getParameterIndex();
        Class<?>[] paramTypes = parameter.getExecutable().getParameterTypes();
        boolean hasBindingResult = (paramTypes.length > (i + 1) && Errors.class.isAssignableFrom(paramTypes[i + 1]));
        return !hasBindingResult;
    }

}
