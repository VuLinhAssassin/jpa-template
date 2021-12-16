package com.vulinh.template;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.function.Supplier;

@NoRepositoryBean
public interface FailFastJpaRepository<I, E extends AbstractEntity> extends JpaRepository<E, I> {

    default E findByIdOrFail(I id, Supplier<? extends RuntimeException> exceptionSupplier) {
        return findById(id).orElseThrow(exceptionSupplier);
    }

    default void existsByIdOrFail(I id, Supplier<? extends RuntimeException> exceptionSupplier) {
        if (!existsById(id)) {
            throw exceptionSupplier.get();
        }
    }
}