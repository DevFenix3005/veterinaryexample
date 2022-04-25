package com.rebirth.veterinaryexample.app.services;

import java.util.List;
import java.util.Optional;

public interface BaseService<CreateModel, UpdateModel, Dto, Key> {

    default Dto create(CreateModel createModel) {
        throw new UnsupportedOperationException("Necesitas implementar este metodo create");
    }

    default Optional<Dto> read(Key uuid) {
        throw new UnsupportedOperationException("Necesitas implementar este metodo read");
    }

    default List<Dto> readAll() {
        throw new UnsupportedOperationException("Necesitas implementar este metodo readAll");
    }

    default Optional<Dto> update(Key uuid, UpdateModel updateModel) {
        throw new UnsupportedOperationException("Necesitas implementar este metodo update");
    }

    default void delete(Key uuid) {
        throw new UnsupportedOperationException("Necesitas implementar este metodo delete");
    }


}
