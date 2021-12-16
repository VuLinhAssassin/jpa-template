package com.vulinh.template;

public interface AbstractValidator<D extends AbstractDTO> {

    default D validateOnCreate(D dto) {
        return dto;
    }

    default D validateOnUpdate(D dto) {
        return dto;
    }
    
}