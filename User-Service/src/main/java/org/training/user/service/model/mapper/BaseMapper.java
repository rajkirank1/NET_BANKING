package org.training.user.service.model.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class BaseMapper<D, E> {

    public abstract E convertToEntity(D dto, Object... args);

    public abstract D convertToDto(E entity, Object... args);

    public Collection<E> convertToEntity(Collection<D> dtoCollection, Object... args) {
        return dtoCollection.stream()
                .map(d -> convertToEntity(d, args))
                .collect(Collectors.toList());
    }

    public Collection<D> convertToDto(Collection<E> entityCollection, Object... args) {
        return entityCollection.stream()
                .map(entity -> convertToDto(entity, args))
                .collect(Collectors.toList());
    }

    public List<E> convertToEntityList(Collection<D> dtoCollection, Object... args) {
        return convertToEntity(dtoCollection, args).stream().collect(Collectors.toList());
    }

    public List<D> convertToDtoList(Collection<E> entityCollection, Object... args) {
        return convertToDto(entityCollection, args).stream().collect(Collectors.toList());
    }

    public Set<E> convertToEntitySet(Collection<D> dtoCollection, Object... args) {
        return convertToEntity(dtoCollection, args).stream().collect(Collectors.toSet());
    }

    public Set<D> convertToDtoSet(Collection<E> entityCollection, Object... args) {
        return convertToDto(entityCollection, args).stream().collect(Collectors.toSet());
    }
}
