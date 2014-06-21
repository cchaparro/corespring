package com.mycompany.controllers;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.mycompany.model.Movimiento;
import com.mycompany.model.Persona;
import com.mycompany.model.TipoMovimiento;
import com.mycompany.repository.PersonaRepository;
import com.mycompany.service.FinancieroException;
import com.mycompany.service.RegistroMovimientos;

@Named
@ViewScoped
public class RegistroMovimientoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private RegistroMovimientos registroMovimientos;
	
	@Inject
	private PersonaRepository personaRepository;
	
	
	private Movimiento movimiento = new Movimiento();
	private List<Persona> todasPersonas;
	
	public void prepararRegistro(){	
		this.todasPersonas = personaRepository.todas();
	}
	
	public void guardar(){

		FacesContext context = FacesContext.getCurrentInstance();
		
		try{
			
			registroMovimientos.guardar(this.movimiento);
			this.movimiento = new Movimiento();
			context.addMessage(null, 
					new FacesMessage("Movimiento guardado "
							+ "con exito."));
			
		}catch(FinancieroException ex){
			
			FacesMessage mensaje = new FacesMessage(ex.getMessage());
			mensaje.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage(null, mensaje);
		}
	}

	public Movimiento getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(Movimiento movimiento) {
		this.movimiento = movimiento;
	}

	public List<Persona> getTodasPersonas() {
		return todasPersonas;
	}

	public void setTodasPersonas(List<Persona> todasPersonas) {
		this.todasPersonas = todasPersonas;
	}
	
	public TipoMovimiento[] getTiposMovimientos(){
		return TipoMovimiento.values();
	}
	
}
