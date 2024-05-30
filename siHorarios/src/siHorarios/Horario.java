package siHorarios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Horario {

	private int TOTAL_PREIODOS;
	private int TOTAL_AULAS;
	private int TOTAL_ASIGNATURAS_CURSO;
	private int TOTAL_CURSOS;
	private int TOTAL_DIAS;
	private Dia[][] dias;
	private Asignatura[][] cursos;
	private int fitness;

	public Horario(int TOTAL_PREIODOS, int TOTAL_AULAS, int TOTAL_CURSOS, int TOTAL_DIAS, int TOTAL_ASIGNATURAS_CURSO) {
		this.TOTAL_ASIGNATURAS_CURSO = TOTAL_ASIGNATURAS_CURSO;
		this.TOTAL_AULAS = TOTAL_AULAS;
		this.TOTAL_CURSOS = TOTAL_CURSOS;
		this.TOTAL_DIAS = TOTAL_DIAS;
		this.TOTAL_PREIODOS = TOTAL_PREIODOS;

		dias = new Dia[TOTAL_DIAS][TOTAL_AULAS * TOTAL_PREIODOS];
		cursos = new Asignatura[TOTAL_CURSOS][];
		fitness = 2000;
	}

	public int getFitness() {
		return fitness;
	}

	public void setFitness(int fitness) {
		this.fitness = fitness;
	}

	public Asignatura[][] getCursos() {
		return cursos;
	}

	public void setCursos(Asignatura[][] cursos) {
		this.cursos = cursos;
	}

	public Dia[][] getDias() {
		return dias;
	}

	public void setDias(Dia[][] dias) {
		this.dias = dias;
	}

	public int getTOTAL_PREIODOS() {
		return TOTAL_PREIODOS;
	}

	public void setTOTAL_PREIODOS(int tOTAL_PREIODOS) {
		TOTAL_PREIODOS = tOTAL_PREIODOS;
	}

	public int getTOTAL_AULAS() {
		return TOTAL_AULAS;
	}

	public void setTOTAL_AULAS(int tOTAL_AULAS) {
		TOTAL_AULAS = tOTAL_AULAS;
	}

	public int getTOTAL_ASIGNATURAS_CURSO() {
		return TOTAL_ASIGNATURAS_CURSO;
	}

	public void setTOTAL_ASIGNATURAS_CURSO(int tOTAL_ASIGNATURAS_CURSO) {
		TOTAL_ASIGNATURAS_CURSO = tOTAL_ASIGNATURAS_CURSO;
	}

	public int getTOTAL_CURSOS() {
		return TOTAL_CURSOS;
	}

	public void setTOTAL_CURSOS(int tOTAL_CURSOS) {
		TOTAL_CURSOS = tOTAL_CURSOS;
	}

	public int getTOTAL_DIAS() {
		return TOTAL_DIAS;
	}

	public void setTOTAL_DIAS(int tOTAL_DIAS) {
		TOTAL_DIAS = tOTAL_DIAS;
	}

	public void hacerAulasPeriodos() {
		for (int i = 0; i < TOTAL_DIAS; i++) {
			for (int j = 0; j < TOTAL_AULAS * TOTAL_PREIODOS; j++) {
				dias[i][j] = new Dia();

			}
		}
		String[] aulas = generarAulas(TOTAL_AULAS);
		for (int i1 = 0; i1 < TOTAL_DIAS; i1++) {
			for (int i = 0; i < TOTAL_AULAS; i++) {
				// Iterar sobre los periodos
				for (int i2 = 0; i2 < TOTAL_PREIODOS; i2++) {
					// Calcular el índice en el array general
					int indice = i * TOTAL_PREIODOS + i2;

					// Asignar el aula al periodo correspondiente
					dias[i1][indice].aula = aulas[i];
				}
			}
		}

		String[] periodos = generarPeriodos(TOTAL_PREIODOS);

		for (int i1 = 0; i1 < TOTAL_DIAS; i1++) {
			for (int i = 0; i < TOTAL_AULAS * TOTAL_PREIODOS; i++) {
				dias[i1][i].setPeriodo(periodos[i % TOTAL_PREIODOS]);
			}
		}

	}

	public void imprimirDias() {
		for (int i = 0; i < TOTAL_DIAS; i++) {
			for (int j = 0; j < TOTAL_AULAS * TOTAL_PREIODOS; j++) {
				int aux = i + 1;
				System.out.println("DIA " + aux + ": Periodo--> " + dias[i][j].getPeriodo() + ", Aula--> "
						+ dias[i][j].getAula() + ", Asignatura --> " + dias[i][j].getAsignatura());
			}
		}
	}

	public String[] generarAulas(int aulas) {
		// Verificar que la distancia sea positiva
		if (aulas <= 0) {
			throw new IllegalArgumentException("La distancia debe ser un número positivo");
		}

		// Definir el prefijo común
		String prefijo = "Aula";

		// Crear el array con la longitud igual a la distancia
		String[] array = new String[aulas];

		// Llenar el array con palabras generadas
		for (int i = 1; i <= aulas; i++) {
			array[i - 1] = prefijo + i;
		}

		return array;
	}

	public String[] generarPeriodos(int periodos) {
		// Verificar que la distancia sea positiva
		if (periodos <= 0) {
			throw new IllegalArgumentException("La distancia debe ser un número positivo");
		}

		// Definir el prefijo común
		String prefijo = "Periodo";

		// Crear el array con la longitud igual a la distancia
		String[] array = new String[periodos];

		// Llenar el array con palabras generadas
		for (int i = 1; i <= periodos; i++) {
			array[i - 1] = prefijo + i;
		}

		return array;
	}

	public Asignatura[] generarAsignaturas(int curso, int asignaturas) {
		// Verificar que tanto el curso como el número de asignaturas sean positivos
		if (curso <= 0 || asignaturas <= 0) {
			throw new IllegalArgumentException("Tanto el curso como el número de asignaturas deben ser positivos");
		}

		// Crear el array de Asignatura con la longitud igual al número de asignaturas
		Asignatura[] array = new Asignatura[asignaturas];

		// Llenar el array con instancias de Asignatura generadas
		for (int i = 1; i <= asignaturas; i++) {
			String nombreAsignatura = "Curso" + curso + "Asignatura" + i;
			array[i - 1] = new Asignatura(nombreAsignatura, "Curso" + curso);
		}

		return array;
	}

	public void imprimirAsignaturas(Horario horario) {
		for (int i = 0; i < TOTAL_CURSOS; i++) {
			for (int j = 0; j < TOTAL_ASIGNATURAS_CURSO; j++) {
				System.out.println(horario.cursos[i][j].toString());
			}
		}
	}

	public void ponerClase(Asignatura a, Horario horario) {
		Random random = new Random();
		// Generar un número aleatorio en el rango de dias
		int dia = random.nextInt(TOTAL_DIAS);
		// Generar un numero aleatroio en el rango de periodos
		int periodo = random.nextInt(TOTAL_PREIODOS * TOTAL_AULAS);
//		periodo = (periodo*5000)%(TOTAL_PREIODOS*TOTAL_AULAS);

		while (horario.dias[dia][periodo].asignatura != null) {
			// Generar un número aleatorio en el rango de dias
			dia = random.nextInt(TOTAL_DIAS);
			// Generar un numero aleatroio en el rango de periodos
			periodo = random.nextInt(TOTAL_PREIODOS);
		}

		horario.dias[dia][periodo].setAsignatura(a);
	}

	public void randHorario(Horario horario) {
		// Mezclar aleatoriamente la lista de asignaturas para aumentar la aleatoriedad
		List<Asignatura> asignaturasAleatorias = new ArrayList<>();
		for (int i = 0; i < TOTAL_CURSOS; i++) {
			for (int j = 0; j < TOTAL_ASIGNATURAS_CURSO; j++) {
				asignaturasAleatorias.add(horario.getCursos()[i][j]);
			}
		}
		Collections.shuffle(asignaturasAleatorias); // Mezclar aleatoriamente las asignaturas

		// Asignar las asignaturas mezcladas aleatoriamente al horario
		for (int i = 0; i < 2; i++) {
			for (Asignatura asignatura : asignaturasAleatorias) {
				ponerClase(asignatura, horario);
			}
		}
	}

	public void randGenes(Horario horario) {
		horario.hacerAulasPeriodos();
		for (int i = 1; i <= TOTAL_CURSOS; i++) {
			horario.cursos[i - 1] = horario.generarAsignaturas(i, TOTAL_ASIGNATURAS_CURSO);
		}
		horario.randHorario(horario);
	}

	public int evaluar2(Horario horario) {
		int newFitnes = 2000;
		for (int dia = 0; dia < TOTAL_DIAS; dia++) {
			// Iterar sobre todos los periodos de cada día
			for (int periodo = 0; periodo < TOTAL_AULAS * TOTAL_PREIODOS; periodo++) {
				// Verificar restricción para el periodo y día actual
				if (!verificarRestriccionDiaPeriodo(horario, dia, periodo)) {
					newFitnes -= 10;
				}
			}
		}
			newFitnes = evaluarRestriccionAsignaturas(horario, newFitnes);
			newFitnes = evaluarRestriccionAsignaturasSeguidas(horario, newFitnes);
			newFitnes = evaluarRestriccionAsignaturasMismoAula(horario, newFitnes);
			newFitnes = evaluarRestriccionAsignaturasMismoAulaSeguidas(horario, newFitnes);
		
		this.setFitness(newFitnes);
		return horario.getFitness();
	}

	private boolean verificarRestriccionDiaPeriodo(Horario horario, int dia, int periodo) {
		// Iterar sobre todas las aulas del periodo y día actual
		for (int aula = 0; aula < TOTAL_AULAS; aula++) {
			// Obtener la asignatura en el periodo, día y aula actual
			Asignatura asignatura = horario.dias[dia][periodo % 6 + (aula * TOTAL_PREIODOS)].getAsignatura();
			if (asignatura != null) {
				String curso = asignatura.getCurso();
				// Iterar sobre las demás aulas del mismo periodo y día
				for (int otraAula = aula + 1; otraAula < TOTAL_AULAS; otraAula++) {
					// Obtener la asignatura en el mismo periodo, día pero en otra aula
					Asignatura otraAsignatura = horario.dias[dia][periodo % 6 + (otraAula * TOTAL_PREIODOS)]
							.getAsignatura();
					// Si hay una asignatura en este aula, periodo y día
					if (otraAsignatura != null) {
						// Obtener el curso de la asignatura en la otra aula
						String otroCurso = otraAsignatura.getCurso();
						// Comprobar si las asignaturas pertenecen al mismo curso
						if (curso.equals(otroCurso)) {
							// Si hay asignaturas del mismo curso en diferentes aulas, mostrar un mensaje de
							// error
							int periodoV = periodo % (TOTAL_PREIODOS);
//	                        System.out.println("Error: En el día " + (dia + 1) + ", en el periodo " + (periodoV+ 1) + ", hay asignaturas del mismo curso en diferentes aulas.");
							return false; // Restricción violada
						}
					}
				}
			}
		}
		return true; // Restricción no violada para el periodo y día actual
	}

	private int evaluarRestriccionAsignaturas(Horario horario, int newFitness) {
		int asignaturasPorCurso = horario.getTOTAL_ASIGNATURAS_CURSO();
		for (int curso = 0; curso < horario.getTOTAL_CURSOS(); curso++) {
			for (int asignatura = 0; asignatura < asignaturasPorCurso; asignatura++) {
				Asignatura asignaturaEvaluar = horario.getCursos()[curso][asignatura];
				int contadorAsig = buscarAsignatura(asignaturaEvaluar, horario);
				if (contadorAsig != 2) {
					newFitness -= 20;
				}
			}
		}
		return newFitness;
	}

	private int buscarAsignatura(Asignatura asignaturaEvaluar, Horario horario) {
		int contador = 0;
		for (int dia = 0; dia < horario.getTOTAL_DIAS(); dia++) {
			for (int periodo = 0; periodo < horario.getTOTAL_AULAS() * horario.getTOTAL_PREIODOS(); periodo++) {
				if (horario.getDias()[dia][periodo].getAsignatura() == asignaturaEvaluar) {
					contador++;
				}
			}
		}
		return contador;
	}

	private int evaluarRestriccionAsignaturasSeguidas(Horario horario, int newFitness) {
		for (int dia = 0; dia < TOTAL_DIAS; dia++) {
			// Iterar sobre todos los periodos de cada día
			for (int periodo = 0; periodo < TOTAL_AULAS * TOTAL_PREIODOS - 1; periodo++) {
				Asignatura asignaturaActual = horario.getDias()[dia][periodo].getAsignatura();
				Asignatura asignaturaSiguiente = horario.getDias()[dia][periodo + 1].getAsignatura();

				// Comprobar si hay dos asignaturas seguidas del mismo curso
				if (asignaturaActual != null && asignaturaSiguiente != null
						&& asignaturaActual.getCurso().equals(asignaturaSiguiente.getCurso())) {
					newFitness += 1; // Añadir penalización a la fitness si hay dos asignaturas del mismo curso
										// seguidas
				}
			}
		}
		return newFitness;
	}

	private int evaluarRestriccionAsignaturasMismoAula(Horario horario, int newFitness) {
		for (int dia = 0; dia < TOTAL_DIAS; dia++) {
			for (int aula = 0; aula < TOTAL_AULAS; aula++) {
				Map<String, Integer> asignaturasPorCurso = new HashMap<>();
				for (int periodo = 0; periodo < TOTAL_PREIODOS; periodo++) {
					Asignatura asignatura = horario.getDias()[dia][aula * TOTAL_PREIODOS + periodo].getAsignatura();
					if (asignatura != null) {
						String curso = asignatura.getCurso();
						// Contar las asignaturas del mismo curso en el mismo aula
						asignaturasPorCurso.put(curso, asignaturasPorCurso.getOrDefault(curso, 0) + 1);
					}
				}
				// Penalizar si hay más de una asignatura del mismo curso en el mismo aula
				for (int count : asignaturasPorCurso.values()) {
					if (count > 1) {
						newFitness += (count / 2); // Sumar 1 por cada par de asignaturas del mismo curso en el mismo
													// aula
					}
				}
			}
		}
		return newFitness;
	}

	private int evaluarRestriccionAsignaturasMismoAulaSeguidas(Horario horario, int newFitness) {
		for (int dia = 0; dia < TOTAL_DIAS; dia++) {
			for (int aula = 0; aula < TOTAL_AULAS; aula++) {
				Asignatura asignaturaAnterior = null;
				for (int periodo = 0; periodo < TOTAL_PREIODOS; periodo++) {
					Dia diaActual = horario.getDias()[dia][aula * TOTAL_PREIODOS + periodo];
					Asignatura asignatura = diaActual.getAsignatura();
					if (asignatura != null && asignaturaAnterior != null) {
						if (asignatura.getCurso().equals(asignaturaAnterior.getCurso())) {
							newFitness += 2; // Sumar 2 a la fitness si hay dos asignaturas seguidas del mismo curso en
												// el mismo aula
						}
					}
					asignaturaAnterior = asignatura;
				}
			}
		}
		return newFitness;
	}

	public void mutacion_adhoc(Horario horario) {
		Random rand = new Random();

		// Seleccionar un día aleatorio
		int diaAleatorio = rand.nextInt(horario.getTOTAL_DIAS());

		// Seleccionar dos períodos aleatorios en el día seleccionado
		int periodo1 = rand.nextInt(horario.getTOTAL_PREIODOS());
		int periodo2 = rand.nextInt(horario.getTOTAL_PREIODOS());

		// Intercambiar las asignaturas en los dos períodos seleccionados
		Dia temp = horario.getDias()[diaAleatorio][periodo1];
		horario.getDias()[diaAleatorio][periodo1] = horario.getDias()[diaAleatorio][periodo2];
		horario.getDias()[diaAleatorio][periodo2] = temp;
	}

	public void mutacion_adhoc2(Horario horario) {
		Random rand = new Random();

		// Seleccionar un día y período aleatorio para la primera asignatura
		int dia1 = rand.nextInt(horario.getTOTAL_DIAS());
		int periodo1 = rand.nextInt(horario.getTOTAL_PREIODOS());

		// Seleccionar un día y período aleatorio para la segunda asignatura, asegurando
		// que sea diferente al primero
		int dia2, periodo2;
		do {
			dia2 = rand.nextInt(horario.getTOTAL_DIAS());
			periodo2 = rand.nextInt(horario.getTOTAL_PREIODOS());
		} while (dia1 == dia2 && periodo1 == periodo2); // Repetir si el día y período seleccionados son iguales

		// Intercambiar las asignaturas entre los dos días y períodos seleccionados
		Dia temp = horario.getDias()[dia1][periodo1];
		horario.getDias()[dia1][periodo1] = horario.getDias()[dia2][periodo2];
		horario.getDias()[dia2][periodo2] = temp;
	}
	
	public void imprimirHorario() {
	    for (int dia = 0; dia < TOTAL_DIAS; dia++) {
	        System.out.println("Día " + (dia + 1) + ":");
	        System.out.println("Aula\t\tPeriodo\t\t\tAsignatura");
	        for (int aula = 0; aula < TOTAL_AULAS; aula++) {
	            for (int periodo = 0; periodo < TOTAL_PREIODOS; periodo++) {
	                Dia diaActual = dias[dia][aula * TOTAL_PREIODOS + periodo];
	                Asignatura asignatura = diaActual.getAsignatura();
	                String nombreAsignatura = (asignatura != null) ? asignatura.getNombre() : "-";
	                System.out.println("Aula " + (aula + 1) + "\t\tPeriodo " + (periodo + 1) + "\t\t" + nombreAsignatura);
	            }
	        }
	        System.out.println();
	    }
	}

	public void imprimirHorario2() {
	    // Recorrer cada curso
	    for (int curso = 0; curso < TOTAL_CURSOS; curso++) {
	        System.out.println("CURSO " + (curso + 1));

	        // Imprimir encabezado de días
	        System.out.print("DÍAS:\t\t");
	        for (int dia = 0; dia < TOTAL_DIAS; dia++) {
	            System.out.print("Día " + (dia + 1) + "\t\t\t");
	        }
	        System.out.println();

	        // Imprimir asignaturas para cada día
	        for (int periodo = 0; periodo < TOTAL_PREIODOS; periodo++) {
	            System.out.print("PERIODO " + (periodo + 1) + ":\t");
	            for (int dia = 0; dia < TOTAL_DIAS; dia++) {
	                boolean asignaturaEncontrada = false;
	                for (int aula = 0; aula < TOTAL_AULAS; aula++) {
	                    Dia diaActual = dias[dia][aula * TOTAL_PREIODOS + periodo];
	                    Asignatura asignatura = diaActual.getAsignatura();
	                    if (asignatura != null && asignatura.getCurso().equals("Curso" + (curso + 1))) {
	                        System.out.print(asignatura.getNombre() + "\t");
	                        asignaturaEncontrada = true;
	                        break;
	                    }
	                }
	                if (!asignaturaEncontrada) {
	                    System.out.print("-\t\t\t");
	                }
	            }
	            System.out.println();
	        }
	        System.out.println();
	    }
	}

	public void imprimirHorario3() {
	    // Recorrer cada curso
	    for (int curso = 0; curso < TOTAL_CURSOS; curso++) {
	        System.out.println("CURSO " + (curso + 1));

	        // Imprimir encabezado de días
	        System.out.print("DÍAS:\t\t");
	        for (int dia = 0; dia < TOTAL_DIAS; dia++) {
	            System.out.print("Día " + (dia + 1) + "\t");
	        }
	        System.out.println();

	        // Imprimir asignaturas para cada día
	        for (int periodo = 0; periodo < TOTAL_PREIODOS; periodo++) {
	            System.out.print("PERIODO " + (periodo + 1) + ":\t");
	            for (int dia = 0; dia < TOTAL_DIAS; dia++) {
	                boolean asignaturaEncontrada = false;
	                for (int aula = 0; aula < TOTAL_AULAS; aula++) {
	                    Dia diaActual = dias[dia][aula * TOTAL_PREIODOS + periodo];
	                    Asignatura asignatura = diaActual.getAsignatura();
	                    if (asignatura != null && asignatura.getCurso().equals("Curso" + (curso + 1))) {
	                        System.out.print(dias[dia][aula * TOTAL_PREIODOS + periodo].getAula()+ "\t");
	                        asignaturaEncontrada = true;
	                        break;
	                    }
	                }
	                if (!asignaturaEncontrada) {
	                    System.out.print("-\t");
	                }
	            }
	            System.out.println();
	        }
	        System.out.println();
	    }
	}
	
	public void imprimirHorarioMixto() {
	    // Recorrer cada curso
	    for (int curso = 0; curso < TOTAL_CURSOS; curso++) {
	        System.out.println("CURSO " + (curso + 1));

	        // Imprimir encabezado de días para asignaturas
	        System.out.print("DÍAS:\t\t");
	        for (int dia = 0; dia < TOTAL_DIAS; dia++) {
	            System.out.print("Día " + (dia + 1) + "\t\t\t");
	        }
	        System.out.println();

	        // Imprimir asignaturas para cada día
	        for (int periodo = 0; periodo < TOTAL_PREIODOS; periodo++) {
	            System.out.print("PERIODO " + (periodo + 1) + "\t");
	            for (int dia = 0; dia < TOTAL_DIAS; dia++) {
	                boolean asignaturaEncontrada = false;
	                for (int aula = 0; aula < TOTAL_AULAS; aula++) {
	                    Dia diaActual = dias[dia][aula * TOTAL_PREIODOS + periodo];
	                    Asignatura asignatura = diaActual.getAsignatura();
	                    if (asignatura != null && asignatura.getCurso().equals("Curso" + (curso + 1))) {
	                        System.out.print(asignatura.getNombre() + "\t");
	                        asignaturaEncontrada = true;
	                        break;
	                    }
	                }
	                if (!asignaturaEncontrada) {
	                    System.out.print("-\t\t\t");
	                }
	            }
	            System.out.println();
	        }
            System.out.println();
	        // Imprimir encabezado de días para aulas
	        System.out.print("DÍAS:\t\t");
	        for (int dia = 0; dia < TOTAL_DIAS; dia++) {
	            System.out.print("Día " + (dia + 1) + "\t");
	        }
	        System.out.println();

	        // Imprimir aulas para cada día
	        for (int periodo = 0; periodo < TOTAL_PREIODOS; periodo++) {
	            System.out.print("PERIODO " + (periodo + 1) + ":\t");
	            for (int dia = 0; dia < TOTAL_DIAS; dia++) {
	                boolean asignaturaEncontrada = false;
	                for (int aula = 0; aula < TOTAL_AULAS; aula++) {
	                    Dia diaActual = dias[dia][aula * TOTAL_PREIODOS + periodo];
	                    Asignatura asignatura = diaActual.getAsignatura();
	                    if (asignatura != null && asignatura.getCurso().equals("Curso" + (curso + 1))) {
	                        System.out.print(dias[dia][aula * TOTAL_PREIODOS + periodo].getAula() + "\t");
	                        asignaturaEncontrada = true;
	                        break;
	                    }
	                }
	                if (!asignaturaEncontrada) {
	                    System.out.print("-\t");
	                }
	            }
	            System.out.println();
	        }

	        System.out.println();
	    }
	}


	public static void main(String[] args) {
//		Horario horario = new Horario();
//		horario.randGenes(horario);
//		horario.imprimirDias();
////		horario.imprimirDia1(horario);
//		System.out.println("---------------------");
//		horario.evaluar2(horario);
//		System.out.println("FITNESS: "+horario.getFitness());

	}

}
