package com.biblioteca.session;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import com.biblioteca.entidad.Libro;

public class LibroSession {
	@PersistenceUnit(name = "BibliotecaPU")
	EntityManager em;

	public List<Libro> buscarTodos() {
		String jpql = "SELECT l FROM libros l ORDER BY l.codigo";
		List<Libro> Clientes = (List<Libro>) em.createQuery(jpql, Libro.class).getResultList();
		return Clientes;
	}

	public Libro buscarPorCodigo(Integer codigo) throws Exception {
		return em.find(Libro.class, codigo);
	}
	public List<Libro> consultarLibroPorDescripcion(String nombre){
		String jpql= "SELECT l from libros l WHERE UPPER(l.descripcion) LIKE : n ORDER BY l.CODIGO ";
		javax.persistence.Query q= em.createQuery(jpql);
		q.setParameter("b", "%"+nombre.toUpperCase() + "%");
		List<Libro> libros=q.getResultList();
		return libros;
		
	}

	public Libro incluir(Libro Libro) {
		em.persist(Libro);
		em.refresh(Libro);
		return Libro;
	}

	public Libro editar(Libro Libro) {
		Libro = em.merge(Libro);
		return Libro;
	}

	private Libro actualizar(Libro ciudad) {
		Libro LibroActualizado = null;
		Libro LibroBuscar = null;
		try {
			LibroBuscar = buscarPorCodigo(ciudad.getCodigo());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Busca el objeto
		if (LibroBuscar == null) { // Si no encuentra ciudad valdrá null
			LibroActualizado = incluir(ciudad);
		} else {
			LibroActualizado = editar(ciudad);
		}
		return LibroActualizado;
	}

	public void eliminar(Integer codigo) {
		Libro LibroBuscar = null;
		try {
			LibroBuscar = buscarPorCodigo(codigo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Busca el objeto ciudad
		if (LibroBuscar != null) {
			em.remove(LibroBuscar);
		}

	}
}
