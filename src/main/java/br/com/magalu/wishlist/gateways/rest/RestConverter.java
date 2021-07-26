package br.com.magalu.wishlist.gateways.rest;

import java.io.Serializable;
import java.util.List;

public interface RestConverter<R extends Serializable, E extends Serializable> {

	default E mapToEntity(final R rest) {
		throw new UnsupportedOperationException();
	}

	default List<R> mapToRest(final List<E> entity) {
		throw new UnsupportedOperationException();
	}
}
