package com.nozama.api.application.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {

  private final ModelMapper mapper = new ModelMapper();

  public <Source, Target> Target mapEntity(Source source, Class<Target> target) {
    return mapper.map(source, target);
  }

  public <Source, Target> List<Target> mapList(List<Source> source, Class<Target> target) {
    if (source.size() == 0) return List.of();

    return source
        .stream()
        .map(s -> this.mapEntity(s, target))
        .toList();
  }
}
