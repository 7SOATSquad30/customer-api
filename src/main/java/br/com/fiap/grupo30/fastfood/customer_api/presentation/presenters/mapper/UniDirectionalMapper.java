package br.com.fiap.grupo30.fastfood.customer_api.presentation.presenters.mapper;

public interface UniDirectionalMapper<S, T> {
    T map(S source);
}
