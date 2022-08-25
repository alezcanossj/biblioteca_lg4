package com.biblioteca.session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.Query;

import com.biblioteca.entidad.Autor;


@Stateless
public class AutorSession {
	@PersistenceContext(name = "BibliotecaPU")
	EntityManager em;
	// CONSULTAR

	public List<Autor> consultarAutores() {
		String jpql = "SELECT a FROM Autor a ORDER BY a.codigo";

		Query q = em.createQuery(jpql);
		List<Autor> autores = q.getResultList();

		return autores;
	}

	// BUSCAR AUTOR CON CODIGO
	public Autor buscarPorCodigo(Integer Codigo) {
		if(Codigo==null) {
			return null;
		}else {
			return em.find(Autor.class, Codigo);
		}
		
	}
	
	public Map<String, Object> consultarAutoresPorNombre (String nombre){
		
		Map<String, Object> retorno = new HashMap<String, Object>();
	try {
		String jpql= "SELECT  a FROM  Autor a "
				+ "WHERE UPPER (a.nombre) LiKE :n "
				+ "ORDER BY a.codigo";
		Query q = em.createQuery(jpql);
		q.setParameter("n","%" + nombre.toUpperCase() + "%" );
		List<Autor> autores = q.getResultList();
		retorno.put("success", true);
		retorno.put("result", autores);
	} catch (Exception e) {
		retorno.put("success", false);
		retorno.put("error", e.getMessage());
	}
		return retorno;
		
	}
	// INCLUIR AUTOR
	public Autor incluir(Autor Autor) {
		em.persist(Autor);
		em.refresh(Autor);
		return Autor;
	}

	public Autor editar(Autor Autor) {
		Autor = em.merge(Autor);
		return Autor;
	}

	// ACTUALIZAR AUTOR
	public Autor Actualizar(Autor Autor) {
		Autor AutorActualizado = null;
		Autor AutorBuscar = null;
		AutorBuscar = buscarPorCodigo(Autor.getCodigo());

		if (AutorBuscar == null) { // Si no encuentra autor valdrá null
			AutorActualizado = incluir(Autor);
		} else {
			AutorActualizado = editar(Autor);
		}
		return AutorActualizado;
	}

	// ELIMINAR AUTOR
	public void Eliminar(Integer Codigo) {
		Autor AutorBuscar = null;
		try {
			AutorBuscar = buscarPorCodigo(Codigo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Busca el objeto Autor
		if (AutorBuscar != null) {
			em.remove(AutorBuscar);
		}
	}
}
