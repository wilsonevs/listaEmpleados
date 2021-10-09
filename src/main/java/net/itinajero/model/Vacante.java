package net.itinajero.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Vacante {

	private Integer id;
	private String nombre;
	private String descripcion;
	private String estatus;
	private Date fecha;
	private Integer destacado;
	private Double salario;
	private String imagen="no-image.png";
	private String detalles;
	private Categoria categoria;

}
