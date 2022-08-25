package com.biblioteca.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import com.biblioteca.entidad.Ciudad;

@Stateless
public class CiudadSession {
	@PersistenceUnit (name= "BibliotecaPU")
	EntityManager em;

	public List<Ciudad> buscarTodos() {
		String jpql = "SELECT c FROM ciudades c ORDER BY c.codigo";
		List<Ciudad> ciudades = (List<Ciudad>) em.createQuery(jpql, Ciudad.class).getResultList();
		return ciudades;
	}

	public Ciudad buscarPorCodigo(Integer codigo) throws Exception {
		return em.find(Ciudad.class, codigo);
	}
	public Ciudad incluir(Ciudad ciudad) {
		em.persist(ciudad);
		em.refresh(ciudad);
		return ciudad;
	}
	public Ciudad editar(Ciudad ciudad) {
		ciudad= em.merge(ciudad);
		return ciudad;
	}
	private Ciudad actualizar(Ciudad ciudad)  {
		Ciudad ciudadActualizado= null;
		Ciudad ciudadBuscar = null;
		try {
			ciudadBuscar = buscarPorCodigo(ciudad.getCodigo());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Busca el objeto
		if (ciudadBuscar == null) { // Si no encuentra ciudad valdrá null
			ciudadActualizado=incluir(ciudad);
		} else {
			ciudadActualizado = editar(ciudad);
		}
		return ciudadActualizado;
	}

	public void eliminar(Integer codigo)  {
		Ciudad ciudadBuscar = null;
		try {
			ciudadBuscar = buscarPorCodigo(codigo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Busca el objeto ciudad
		if (ciudadBuscar != null) {
			em.remove(ciudadBuscar);
		}
	}
}
