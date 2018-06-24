package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ar.edu.unlam.tallerweb1.dao.UsuarioDao;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

@Service("servicioPruebas")
@Transactional

public class ServicioPruebasImpl implements ServicioPruebas{
	
	@Inject
	private UsuarioDao daoUsuario;



	@Override
	public List<Usuario> listarUsuarios() {
		return daoUsuario.listarUsuarios();
	}



	@Override
	public Usuario Buscar(Long uid) {
		return daoUsuario.buscarUsuarioPorId(uid);
	}
	

}
