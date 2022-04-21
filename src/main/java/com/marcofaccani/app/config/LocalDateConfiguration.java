package com.marcofaccani.app.config;

import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.data.convert.Jsr310Converters.StringToLocalDateConverter;
import org.springframework.data.convert.Jsr310Converters.StringToLocalDateTimeConverter;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;

@Component
public class LocalDateConfiguration implements RepositoryRestConfigurer {

  @Override
  public void configureConversionService(ConfigurableConversionService conversionService) {
    conversionService.addConverter(StringToLocalDateConverter.INSTANCE);
    conversionService.addConverter(StringToLocalDateTimeConverter.INSTANCE);
  }

}