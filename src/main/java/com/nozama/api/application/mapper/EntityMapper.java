package com.nozama.api.application.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EntityMapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static <Source, Target> Target mapEntity(Source source, Class<Target> target) {
        return mapper.map(source, target);
    }

    public static <Source, Target> List<Target> mapList(List<Source> source, Class<Target> target) {
        if(source.size() == 0) return List.of();

        return source
            .stream()
            .map(s -> EntityMapper.mapEntity(s, target))
            .toList();
    }
}
