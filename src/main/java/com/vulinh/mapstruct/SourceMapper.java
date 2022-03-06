package com.vulinh.mapstruct;

import com.vulinh.template.AbstractMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

/* Builder has some limitations regarding inheritance, so mark this to use default getter/setter */
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface SourceMapper extends AbstractMapper<ExampleEntity, ExampleDTO> {
}