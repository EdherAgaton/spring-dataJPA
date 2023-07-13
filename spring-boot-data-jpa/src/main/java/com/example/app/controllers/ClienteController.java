package com.example.app.controllers;

import javax.naming.Binding;

import org.apache.logging.log4j.util.StringBuilderFormattable;
import org.hibernate.query.sqm.tree.SqmDeleteOrUpdateStatement;
import org.hibernate.validator.cfg.context.ReturnValueConstraintMappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.models.dao.IClienteDao;
import com.example.app.models.entity.Cliente;
import com.example.app.models.service.IClienteService;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("cliente")
public class ClienteController {
	
	@Autowired
	private IClienteService clienteService;
	
	@GetMapping("/listar")
	public String listar(Model model) {
		
		model.addAttribute("titulo", "Listado de Clientes");
		model.addAttribute("clientes", clienteService.findAll());
		
		return "listar";
		
	}
	
	@GetMapping("/form")
	public String crear(Model model) {
		
		Cliente cliente = new Cliente();
		
		
		model.addAttribute("titulo", "Formulario Cliente");
		model.addAttribute("cliente", cliente);
		return "form";
	}
	
	@PostMapping("/form")
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model, RedirectAttributes flash ,SessionStatus sessionStatus) {
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "Formulario Cliente");
			return "form";
		}
		
		clienteService.save(cliente);
		
		//Para el mensjae de confirmacion de guardado
		String mensajeFlash = (cliente.getId() != null)? "Cliente editado con exito" : "Cliente creado con exito";
		
		sessionStatus.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		
		return "redirect:/listar";
	}
	
	
	//EDITAR
	@GetMapping("/form/{id}")
	public String editar(@PathVariable (value = "id") Long id,  Model model, RedirectAttributes flash) {
		
		Cliente cliente = null;
		
		if(id>0) {
			
			cliente = clienteService.findOne(id);
			
		}else {
			flash.addFlashAttribute("error", "Cliente con id invalido");
			return "listar";
		}
		
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Formulario de Editar Cliente");
		
		return "form";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminarString(@PathVariable (value = "id") Long id, Model model, RedirectAttributes flash) {
		
		if(id > 0) {
			clienteService.delete(id);
		}
		

		flash.addFlashAttribute("success", "Cliente eliminado con exito");
		
		
		return "redirect:/listar";
	}
	

}
