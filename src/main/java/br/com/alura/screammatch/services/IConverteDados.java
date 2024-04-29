package br.com.alura.screammatch.services;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
