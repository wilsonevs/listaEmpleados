package net.itinajero.service;

import java.util.List;

import net.itinajero.model.Categoria;

public interface ICategoriaService {
	void guardar(Categoria categoria);
	List<Categoria> buscarTodas();
	Categoria buscarPorId(Integer idCategoria);
}
