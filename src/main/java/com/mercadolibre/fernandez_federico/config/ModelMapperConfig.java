package com.mercadolibre.fernandez_federico.config;

import java.util.List;
import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig
{
    @Bean
    public ModelMapper bean() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper;
    }



   /* public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> bean().map(element, targetClass))
                .collect(Collectors.toList());
    }*/
}