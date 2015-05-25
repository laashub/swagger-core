package com.wordnik.swagger.jaxrs.ext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.models.parameters.Parameter;

public abstract class AbstractSwaggerExtension implements SwaggerExtension {

  @Override
  public String extractOperationMethod(ApiOperation apiOperation, Method method, Iterator<SwaggerExtension> chain) {
    if (chain.hasNext())
      return chain.next().extractOperationMethod(apiOperation, method, chain);
    else
      return null;
  }

  @Override
  public List<Parameter> extractParameters(List<Annotation> annotations, Type type, Set<Type> typesToSkip,
      Iterator<SwaggerExtension> chain) {
    if (chain.hasNext())
      return chain.next().extractParameters(annotations, type, typesToSkip, chain);
    else
      return Collections.emptyList();
  }

  protected boolean shouldIgnoreClass(Class<?> cls) {
    return false;
  }

  protected boolean shouldIgnoreType(Type type, Set<Type> typesToSkip) {
    if (typesToSkip.contains(type)) {
      return true;
    }
    if (shouldIgnoreClass(constructType(type).getRawClass())) {
      typesToSkip.add(type);
      return true;
    }
    return false;
  }

  protected JavaType constructType(Type type) {
    return TypeFactory.defaultInstance().constructType(type);
  }
}
