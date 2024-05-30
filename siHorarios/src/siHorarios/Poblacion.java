package siHorarios;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;



public class Poblacion {
	private static Random m_rand = new Random(); // random-number generator
	int ELITISM_K;
	int POP_SIZE; // population size
	int MAX_ITER; // max number of iterations
	double MUTATION_RATE; // probability of mutation
	double CROSSOVER_RATE; // probability of crossover
	
	private Horario[] poblacion;
	private double totalFitness;
	
	
	public Poblacion(int _ELITISM_K, int _POP_SIZ, int _MAX_ITER,
			double _MUTATION_RATE, double _CROSSOVER_RATE, int TOTAL_PREIODOS,
			int TOTAL_AULAS, int TOTAL_CURSOS, int TOTAL_DIAS, int TOTAL_ASIGNATURAS_CURSO) {
		
		this.ELITISM_K = _ELITISM_K;
		this.POP_SIZE = _POP_SIZ;
		this.MAX_ITER = _MAX_ITER;
		this.MUTATION_RATE = _MUTATION_RATE;
		this.CROSSOVER_RATE = _CROSSOVER_RATE;
		

		poblacion = new Horario[_POP_SIZ];
		
		//Iniciar poblacion
		for(int i = 0; i<_POP_SIZ;i++) {
			poblacion[i] = new Horario( TOTAL_PREIODOS,  TOTAL_AULAS,  TOTAL_CURSOS,  TOTAL_DIAS,  TOTAL_ASIGNATURAS_CURSO);
			poblacion[i].randGenes(poblacion[i]);
		}
		
		this.evaluate();
	}
	
//	public Horario rouletteWheelSelection() {
//	    double minFitness = Double.POSITIVE_INFINITY;
//	    double maxFitness = Double.NEGATIVE_INFINITY;
//
//	    // Encuentra el mínimo y el máximo de la función de aptitud
//	    for (int i = 0; i < POP_SIZE; i++) {
//	        double fitness = poblacion[i].getFitness();
//	        if (fitness < minFitness) {
//	            minFitness = fitness;
//	        }
//	        if (fitness > maxFitness) {
//	            maxFitness = fitness;
//	        }
//	    }
//
//	    // Ajusta la función de aptitud para que sea siempre no negativa
//	    double sumaTotal = 0.0;
//	    if (minFitness < 0) {
//	    	sumaTotal = Math.abs(minFitness);
//	        maxFitness += sumaTotal;
//	    }
//
//	    // Calcular la suma total de la función de aptitud ajustada
//	    double totalAdjustedFitness = 0.0;
//	    for (int i = 0; i < POP_SIZE; i++) {
//	        totalAdjustedFitness += (poblacion[i].getFitness() + sumaTotal);
//	    }
//
//	    // Selección de la ruleta
//	    double randNum = m_rand.nextDouble() * totalAdjustedFitness;
//	    int idx;
//	    for (idx = 0; idx < POP_SIZE && randNum > 0; ++idx) {
//	        randNum -= (poblacion[idx].getFitness() + sumaTotal);
//	    }
//
//	    return poblacion[idx - 1];
//	}

	public Horario rouletteWheelSelection() {
		double randNum = m_rand.nextDouble() * this.totalFitness;
		int idx;
		for (idx = 0; idx < POP_SIZE && randNum > 0; ++idx) {
			randNum -= poblacion[idx].getFitness();
		}
		return poblacion[idx - 1];
	}
	
	public Horario[] getPoblacion() {
		return poblacion;
	}
	
	public Horario rankSelection() {
		Horario[] sortedPop = new Horario[POP_SIZE];
		for (int i = 0; i < POP_SIZE; i++) {
	        sortedPop[i] = this.getPoblacion()[i]; 
	    }
		Arrays.sort(sortedPop, Comparator.comparingDouble(Horario::getFitness).reversed());

		
		double sumaDeRangos = (POP_SIZE * (POP_SIZE + 1)) / 2.0;

        // Generar un número aleatorio en el rango de la suma de los rangos
        double numeroAleatorio = m_rand.nextDouble() * sumaDeRangos;

        // Iterar sobre la población para encontrar el individuo seleccionado
        double rangoAcumulado = 0.0;
        for (int i = 0; i < POP_SIZE; i++) {
            // Incrementar el rango acumulado con el rango del individuo en la posición i
            rangoAcumulado += (i + 1);

            // Si el rango acumulado supera el número aleatorio generado,
            // seleccionamos este individuo
            if (rangoAcumulado >= numeroAleatorio) {
                return poblacion[i];
            }
        }

        // Si no se seleccionó ningún individuo durante el bucle, devolvemos el último individuo
        return poblacion[POP_SIZE - 1];
		
		
	}
	
	public Horario seleccionTorneo() {
        Horario mejorIndividuo = null;

        // Realizar dos torneos
        for (int i = 0; i < 2; i++) {
            // Seleccionar aleatoriamente dos individuos para el torneo
            Horario individuo1 = poblacion[m_rand.nextInt(POP_SIZE)];
            Horario individuo2 = poblacion[m_rand.nextInt(POP_SIZE)];

            // Seleccionar al individuo con el mayor fitness
            Horario ganador = (individuo1.getFitness() > individuo2.getFitness()) ? individuo1 : individuo2;

            // Actualizar al mejor individuo si es nulo o si el ganador del torneo actual tiene mayor fitness
            if (mejorIndividuo == null || ganador.getFitness() > mejorIndividuo.getFitness()) {
                mejorIndividuo = ganador;
            }
        }

        return mejorIndividuo;
    }

	public Horario seleccionTruncada() {
		int n_descartat = m_rand.nextInt(16) + 5; 
		double[] fitnessPoblacion = new double[POP_SIZE];
        for (int i = 0; i < POP_SIZE; i++) {
            fitnessPoblacion[i] = poblacion[i].getFitness();
        }

        // Descartar los primeros N_DESCARTAR individuos con menor fitness
        double[] poblacionTruncada = new double[POP_SIZE - n_descartat];
        Arrays.sort(fitnessPoblacion);
        double[] poblacionOrdenada = new double[POP_SIZE];
        for (int i = 0; i < POP_SIZE; i++) {
            poblacionOrdenada[i] = fitnessPoblacion[POP_SIZE - 1 - i];
        }
        System.arraycopy(fitnessPoblacion, n_descartat, poblacionTruncada, 0, poblacionTruncada.length);

        // Seleccionar aleatoriamente un individuo de los restantes
        int indiceSeleccionado = m_rand.nextInt(poblacionTruncada.length);

        // Encontrar el individuo seleccionado en la población original y devolverlo
        for (int i = 0; i < POP_SIZE; i++) {
            if (poblacion[i].getFitness() == poblacionTruncada[indiceSeleccionado]) {
                return poblacion[i];
            }
        }
        return null;
	}
	
	public Horario rouletteWheelSelection2() {
		Horario[] sortedPop = new Horario[POP_SIZE];
		for (int i = 0; i < POP_SIZE; i++) {
	        sortedPop[i] = this.getPoblacion()[i]; 
	    }
		Arrays.sort(sortedPop, Comparator.comparingDouble(Horario::getFitness).reversed());

        int mejores = (int) (sortedPop.length * 0.05);
        double totalFitness = 0;
        for (int i = mejores; i < sortedPop.length; i++) {
            totalFitness += sortedPop[i].getFitness();
        }

        double randomValue = Math.random() * totalFitness;
        double sum_acumlualda = 0;
        for (int i = mejores; i < sortedPop.length; i++) {
        	sum_acumlualda += sortedPop[i].getFitness();
            if (sum_acumlualda >= randomValue) {
                return sortedPop[i];
            }
        }
        
        return null; //Aunque nunca deberia llegar aqui 
		
	}
	


	public void setPoblacion(Horario[] poblacion) {
		this.poblacion = poblacion;
	}


	public double evaluate() {
		this.totalFitness = 0.0;
		for (int i = 0; i < POP_SIZE; i++) {
			this.totalFitness += poblacion[i].evaluar2(poblacion[i]);
		}
		return this.totalFitness;
	}
	public double gettotalFirness() {
		return this.totalFitness;
	}
	
	public Horario findBestIndividual() {
		int idxMax = 0, idxMin = 0;
		double currentMax = 0.0;
		double currentMin = 1.0;
		double currentVal;

		for (int idx = 0; idx < POP_SIZE; ++idx) {
			currentVal = poblacion[idx].getFitness();
			if (currentMax < currentMin) {
				currentMax = currentMin = currentVal;
				idxMax = idxMin = idx;
			}
			if (currentVal > currentMax) {
				currentMax = currentVal;
				idxMax = idx;
			}
			if (currentVal < currentMin) {
				currentMin = currentVal;
				idxMin = idx;
			}
		}

		// return m_population[idxMin]; // minimization
		return poblacion[idxMax]; // maximization
	}
	public static void main(String[] args) {
		
//		Poblacion pop = new Poblacion(50);
//		for(int i = 0; i< pop.POP_SIZE; i++) {
//			pop.poblacion[i].evaluar2(pop.poblacion[i]);
//			}
//		
//		for(int i = 0; i<pop.POP_SIZE; i++) {
//			System.out.println("Fitness horario"+ i + " :" +pop.poblacion[i].getFitness());
//			}
//		System.out.println("FIN");
//	}

	}

	public double getm_rand() {
		return this.m_rand.nextDouble();
	}

	public Horario[] crossover_unPunto(Horario horario, Horario horario2) {
		Horario[] newIndiv = new Horario[2];
		newIndiv[0] = new Horario(horario.getTOTAL_PREIODOS(),horario.getTOTAL_AULAS(),horario.getTOTAL_CURSOS(),horario.getTOTAL_DIAS(),horario.getTOTAL_ASIGNATURAS_CURSO());
		newIndiv[1] = new Horario(horario.getTOTAL_PREIODOS(),horario.getTOTAL_AULAS(),horario.getTOTAL_CURSOS(),horario.getTOTAL_DIAS(),horario.getTOTAL_ASIGNATURAS_CURSO());
		newIndiv[0].setCursos(horario.getCursos());
	    newIndiv[1].setCursos(horario2.getCursos());
		
		Random r = new Random();
		int medio = r.nextInt(horario.getTOTAL_DIAS()); 

      //Nuevo 1
        for (int i = 0; i <= medio; i++) {
            System.arraycopy(horario.getDias()[i], 0, newIndiv[0].getDias()[i], 0, horario.getDias()[0].length);
        }
        for (int i = medio + 1; i < horario.getTOTAL_DIAS(); i++) {
            System.arraycopy(horario2.getDias()[i], 0, newIndiv[0].getDias()[i], 0, horario2.getDias()[0].length);
        }

        //Nuevo 2
        for (int i = 0; i <= medio; i++) {
            System.arraycopy(horario2.getDias()[i], 0, newIndiv[1].getDias()[i], 0, horario2.getDias()[0].length);
        }
        for (int i = medio + 1; i < horario.getTOTAL_DIAS(); i++) {
            System.arraycopy(horario.getDias()[i], 0, newIndiv[1].getDias()[i], 0, horario.getDias()[0].length);
        }

        
        

		return newIndiv;
	}
	
	public Horario[] crossover_dosPuntos(Horario horario, Horario horario2) {
	    Horario[] newIndiv = new Horario[2];
	    newIndiv[0] = new Horario(horario.getTOTAL_PREIODOS(), horario.getTOTAL_AULAS(), horario.getTOTAL_CURSOS(), horario.getTOTAL_DIAS(), horario.getTOTAL_ASIGNATURAS_CURSO());
	    newIndiv[1] = new Horario(horario.getTOTAL_PREIODOS(), horario.getTOTAL_AULAS(), horario.getTOTAL_CURSOS(), horario.getTOTAL_DIAS(), horario.getTOTAL_ASIGNATURAS_CURSO());
	    newIndiv[0].setCursos(horario.getCursos());
	    newIndiv[1].setCursos(horario2.getCursos());
	    
	    Random r = new Random();
	    int punto1 = r.nextInt(horario.getTOTAL_DIAS()); // Primer punto de cruce
	    int punto2 = r.nextInt(horario.getTOTAL_DIAS()); // Segundo punto de cruce
	    if (punto1 > punto2) { // Aseguramos que punto1 sea menor que punto2
	        int temp = punto1;
	        punto1 = punto2;
	        punto2 = temp;
	    }

	    // Nuevo 1
	    for (int i = 0; i <= punto1; i++) {
	        System.arraycopy(horario.getDias()[i], 0, newIndiv[0].getDias()[i], 0, horario.getDias()[0].length);
	    }
	    for (int i = punto1 + 1; i <= punto2; i++) {
	        System.arraycopy(horario2.getDias()[i], 0, newIndiv[0].getDias()[i], 0, horario2.getDias()[0].length);
	    }
	    for (int i = punto2 + 1; i < horario.getTOTAL_DIAS(); i++) {
	        System.arraycopy(horario.getDias()[i], 0, newIndiv[0].getDias()[i], 0, horario.getDias()[0].length);
	    }

	    // Nuevo 2
	    for (int i = 0; i <= punto1; i++) {
	        System.arraycopy(horario2.getDias()[i], 0, newIndiv[1].getDias()[i], 0, horario2.getDias()[0].length);
	    }
	    for (int i = punto1 + 1; i <= punto2; i++) {
	        System.arraycopy(horario.getDias()[i], 0, newIndiv[1].getDias()[i], 0, horario.getDias()[0].length);
	    }
	    for (int i = punto2 + 1; i < horario.getTOTAL_DIAS(); i++) {
	        System.arraycopy(horario2.getDias()[i], 0, newIndiv[1].getDias()[i], 0, horario2.getDias()[0].length);
	    }

	    return newIndiv;
	}
	
	public Horario[] crossover_uniforme(Horario horario, Horario horario2) {
	    Horario[] newIndiv = new Horario[2];
	    newIndiv[0] = new Horario(horario.getTOTAL_PREIODOS(), horario.getTOTAL_AULAS(), horario.getTOTAL_CURSOS(), horario.getTOTAL_DIAS(), horario.getTOTAL_ASIGNATURAS_CURSO());
	    newIndiv[1] = new Horario(horario.getTOTAL_PREIODOS(), horario.getTOTAL_AULAS(), horario.getTOTAL_CURSOS(), horario.getTOTAL_DIAS(), horario.getTOTAL_ASIGNATURAS_CURSO());
	    newIndiv[0].setCursos(horario.getCursos());
	    newIndiv[1].setCursos(horario2.getCursos());
	    
	    Random r = new Random();

	    for (int i = 0; i < horario.getTOTAL_DIAS(); i++) {
	        for (int j = 0; j < horario.getDias()[0].length; j++) {
	            if (r.nextDouble() < 0.5) { 
	                newIndiv[0].getDias()[i] = Arrays.copyOf(horario.getDias()[i], horario.getDias()[i].length);
	                newIndiv[1].getDias()[i] = Arrays.copyOf(horario2.getDias()[i], horario2.getDias()[i].length);
	            } else {
	                newIndiv[0].getDias()[i] = Arrays.copyOf(horario2.getDias()[i], horario2.getDias()[i].length);
	                newIndiv[1].getDias()[i] = Arrays.copyOf(horario.getDias()[i], horario.getDias()[i].length);
	            }
	        }
	    }

	    return newIndiv;
	}

}
