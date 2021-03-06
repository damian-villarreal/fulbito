package ar.edu.unlam.tallerweb1.controladores;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.Cupo;
import ar.edu.unlam.tallerweb1.modelo.Partido;
import ar.edu.unlam.tallerweb1.modelo.Puntos;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioCupo;
import ar.edu.unlam.tallerweb1.servicios.ServicioPartido;
import ar.edu.unlam.tallerweb1.servicios.ServicioPuntos;



@Controller
public class ControladorPartido {
	
	@Inject
	private ServicioPartido servicioPartido;
	
	
	@Inject
	private ServicioPuntos servicioPuntos;
	

	@Inject
	private ServicioCupo servicioCupo;

	@RequestMapping("/crearPartido")
	public ModelAndView CrearPartido() {
		Partido partido = new Partido();
		ModelMap model = new ModelMap();
		model.put("partido",partido);
		return new ModelAndView("crearPartido", model);
	}
	
	@RequestMapping("/buscarPartidos")
	public ModelAndView buscarPartidos() {
		ModelMap model = new ModelMap();
		model.put("resultado", servicioPartido.buscar());
		return new ModelAndView("buscarPartidos", model);
	}
	
	@RequestMapping(path = "/detallePartido/{idPartido}")
		public ModelAndView detallePartido(@PathVariable long idPartido) {
			ModelMap model = new ModelMap();
			model.put("partido", servicioPartido.buscarId(idPartido));
			model.put("cupos", servicioCupo.listarCuposPorPartido(idPartido));
			return new ModelAndView("detallePartido",model);
			
		}
	
	

		@RequestMapping(path = "/finalizarPartido/{idPartido}")
	public ModelAndView finalizarPartido(@PathVariable long idPartido) {
			
		ModelMap model = new ModelMap();
		
		
		
		List<Cupo> listaDeCuposPorPartido = servicioCupo.listarCuposPorPartido(idPartido);
		
		
		
		model.put("cupoClave",listaDeCuposPorPartido);
			return  new ModelAndView("calificarJugadores",model);

	}
		
	
		//le paso asigno el idcupo a un objeto puntaje,ese puntaje me va a guardar los campos de puntos para ese idcupo 
		
	@RequestMapping(path = "/calificarJugador/{idCupo}/{nombre}/{apellido}/{posicion}")
	public ModelAndView calificarJugador(@PathVariable long idCupo,@PathVariable String nombre,@PathVariable String apellido,@PathVariable String posicion) {
			
		ModelMap model = new ModelMap();
		
		//Usuario usuario=servicioUsuario.buscarUsuarioPorId(idUsuario);
		
		Puntos puntos = new Puntos();


		model.put("claveIdCupo", idCupo);

		model.put("claveNombre", nombre);

		model.put("claveApellido", apellido);
		model.put("clavePosicion", posicion);
		
		model.put("objetoPuntos", puntos);
		
		
		/*model.put("jugador",usuario);*/
			return  new ModelAndView("Puntuacion",model);


		}

	@RequestMapping(path = "/PuntajeGuardado", method = RequestMethod.POST)
	public ModelAndView CalificacionGuardada(@ModelAttribute("objetoPuntos") Puntos puntaje, HttpServletRequest request) {
		ModelMap model = new ModelMap();
		
	
	//	model.put("objetoPuntos", puntaje);
		
		
		
		//servicio que haga la conversion a puntos y devuelva un solo valor de puntuacion
		
		servicioPuntos.Alta(puntaje);
		
		int Total=servicioPuntos.PuntajeResultado(puntaje);
			model.put("total", Total);
		

		return new ModelAndView("PuntajeGuardado", model);
	}
	
	@RequestMapping(path = "/nuevoPartido",method=RequestMethod.POST)
	public ModelAndView crear(@ModelAttribute("partido") Partido partido, HttpServletRequest request){
		boolean registro = servicioPartido.nuevoPartido(partido,((long)request.getAttribute("uid")));
		if(registro) {
			return new ModelAndView("redirect:/index");
		}else {
			return null;
		}
	}
	
	}
