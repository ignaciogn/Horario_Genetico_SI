package siHorarios;

public class Asignatura {

	private String nombre;
	private String curso;

	public Asignatura() {
		nombre = null;
		curso = null;
	}

	public Asignatura(String nombre, String curso) {
		this.nombre = nombre;
		this.curso = curso;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	@Override
	public String toString() {
		return "Asignatura [nombre=" + nombre + ", curso=" + curso + "]";
	}
	


		

}
