package siHorarios;

public class Dia {
	
	String periodo;
	String aula;
	Asignatura asignatura;
	
	public Dia() {
		aula=null;
		asignatura=null;
		periodo = null;
	}


	public Dia(String periodo, String aula, Asignatura asignatura) {
		super();
		this.periodo = periodo;
		this.aula = aula;
		this.asignatura = asignatura;
	}



	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public Asignatura getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}

	public String getAula() {
		return aula;
	}

	public void setAula(String aula) {
		this.aula = aula;
	}


	@Override
	public String toString() {
		return "[periodo=" + periodo + ", aula=" + aula + ", asignatura=" + asignatura + "]";
	}


	

}
