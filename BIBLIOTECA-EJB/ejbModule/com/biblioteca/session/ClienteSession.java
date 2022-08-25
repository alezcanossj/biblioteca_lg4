package com.biblioteca.session;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import com.biblioteca.entidad.Cliente;

@Stateless
public class ClienteSession {

	@PersistenceUnit(name = "BibliotecaPU")
	EntityManager em;

	public List<Cliente> buscarTodos() {
		String jpql = "SELECT c FROM clientes c ORDER BY c.codigo";
		List<Cliente> Clientes = (List<Cliente>) em.createQuery(jpql, Cliente.class).getResultList();
		return Clientes;
	}

	public Cliente buscarPorCodigo(Integer codigo) throws Exception {
		return em.find(Cliente.class, codigo);
	}
	public List<Cliente> consultarClientesPorNombre(String nombre){
		String jpql= "SELECT c from clientes c WHERE UPPER(c.nombre) LIKE : n ORDER BY c.CODIGO ";
		javax.persistence.Query q= em.createQuery(jpql);
		q.setParameter("b", "%"+nombre.toUpperCase() + "%");
		List<Cliente> clientes=q.getResultList();
		return clientes;
		
	}

	public Cliente incluir(Cliente Cliente) {
		em.persist(Cliente);
		em.refresh(Cliente);
		return Cliente;
	}

	public Cliente editar(Cliente Cliente) {
		Cliente = em.merge(Cliente);
		return Cliente;
	}

	private Cliente actualizar(Cliente ciudad) {
		Cliente clienteActualizado = null;
		Cliente clienteBuscar = null;
		try {
			clienteBuscar = buscarPorCodigo(ciudad.getCodigo());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Busca el objeto
		if (clienteBuscar == null) { // Si no encuentra ciudad valdrá null
			clienteActualizado = incluir(ciudad);
		} else {
			clienteActualizado = editar(ciudad);
		}
		return clienteActualizado;
	}

	public void eliminar(Integer codigo) {
		Cliente clienteBuscar = null;
		try {
			clienteBuscar = buscarPorCodigo(codigo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Busca el objeto ciudad
		if (clienteBuscar != null) {
			em.remove(clienteBuscar);
		}

	}
}
