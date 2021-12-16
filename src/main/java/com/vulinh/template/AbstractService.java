package com.vulinh.template;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.function.Supplier;

@RequiredArgsConstructor
public abstract class AbstractService<
        I, E extends AbstractEntity, D extends AbstractDTO,
        V extends AbstractValidator<D>, M extends AbstractMapper<E, D>, R extends FailFastJpaRepository<I, E>
        > {

    protected D findById(I id) {
        return mapper.toDto(repository.findByIdOrFail(id, notFoundExceptionSupplier));
    }

    protected Page<D> findAll() {
        return findAll(Pageable.unpaged());
    }

    protected Page<D> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }

    protected D create(D dto) {
        return performSaveOrUpdate(validator.validateOnCreate(dto));
    }

    protected D update(D dto) {
        return performSaveOrUpdate(validator.validateOnUpdate(dto));
    }

    protected D delete(I id) {
        E deletedEntity = repository.findByIdOrFail(id, notFoundExceptionSupplier);
        return delete(deletedEntity);
    }

    protected D delete(E entity) {
        entity.markAsRemoved();
        return mapper.toDto(repository.saveAndFlush(entity));
    }

    protected void deleteAllByIds(Iterable<I> ids) {
        // Must override the method in implemented JpaRepository class in order to execute soft delete!!!
        // Using query: update Entity e set isDeleted = 1 where e.id in :ids and (isDeleted != 1)
        repository.deleteAllByIdInBatch(ids);
    }

    protected void deleteAll(Iterable<E> entities) {
        // Must override the method in implemented JpaRepository class in order to execute soft delete!!!
        // Using query: update Entity e set isDeleted = 1 where e.id in :ids and (isDeleted != 1)
        repository.deleteAllInBatch(entities);
    }

    private D performSaveOrUpdate(D dto) {
        E entity = mapper.toEntity(dto);
        return mapper.toDto(repository.saveAndFlush(entity));
    }

    private final Supplier<? extends RuntimeException> notFoundExceptionSupplier;
    private final V validator;
    private final M mapper;
    private final R repository;

}