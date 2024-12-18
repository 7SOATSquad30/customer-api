package br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.mapper;

public interface BiDirectionalMapper<S, T> {
    T mapTo(S source);

    S mapFrom(T target);
}
