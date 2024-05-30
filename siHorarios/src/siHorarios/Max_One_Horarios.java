package siHorarios;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Max_One_Horarios {

	public static void main(String[] args) {

		int ELITISM_K = 20; // 0 sin elimisto 1 con elitimos puro, solo pasa el primero
		int POP_SIZE = 100 + ELITISM_K; // population size
		int MAX_ITER = 800; // max number of iterations
		double MUTATION_RATE = 0.05; // probability of mutation
		double CROSSOVER_RATE = 1; // probability of crossover
		int TOTAL_PREIODOS = 6;
		int TOTAL_AULAS = 6;
		int TOTAL_ASIGNATURAS_CURSO = 6;
		int TOTAL_DIAS = 5;
		int TOTAL_CURSOS = 4;
		int ESTRATEGIA_REMPLAZAMIENTO = 0;
		int OPERADOR_CRUCE = 2;
		int OPERADOT_MUTATION = 1;
		int ALGORITMO_SELECCION = 2;

		String filename = "config.txt";

		try {
			FileReader fileReader = new FileReader(filename);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] parts = line.split(":");
				if (parts.length == 2) {
					String key = parts[0].trim();
					String value = parts[1].trim();
					switch (key) {
					case "Population Size":
						POP_SIZE = Integer.parseInt(value);
						break;
					case "Generations":
						MAX_ITER = Integer.parseInt(value);
						break;
					case "Crossover Probability":
						CROSSOVER_RATE = Double.parseDouble(value);
						break;
					case "Mutation Probability":
						MUTATION_RATE = Double.parseDouble(value);
						break;
					case "Elitism":
						ELITISM_K = Integer.parseInt(value);
						break;
					case "Replacement Strategy":
						ESTRATEGIA_REMPLAZAMIENTO = Integer.parseInt(value);
						break;
					case "Crossover Operator":
						OPERADOR_CRUCE = Integer.parseInt(value);
						break;
					case "Mutation Operator":
						OPERADOT_MUTATION = Integer.parseInt(value);
						break;
					case "Selection Algorithm":
						ALGORITMO_SELECCION = Integer.parseInt(value);
						break;
					case "Number of courses":
						TOTAL_CURSOS = Integer.parseInt(value);
						break;
					case "Number of subjects per course":
						TOTAL_ASIGNATURAS_CURSO = Integer.parseInt(value);
						break;
					case "Number of hours per subject":
						// No se utiliza actualmente en el programa, aqui en vez de las horas quiero implementar
						// hacer dinamico el numero de periodo por asignaturas o por horas
						break;
					case "Number of classrooms":
						TOTAL_AULAS = Integer.parseInt(value);
						break;
					case "Number of days":
						TOTAL_DIAS = Integer.parseInt(value);
						break;
					case "Number of periods per day":
						TOTAL_PREIODOS = Integer.parseInt(value);
						break;
					default:
						System.err.println("Clave no reconocida: " + key);
					}
				}
			}
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		POP_SIZE += ELITISM_K;
		Poblacion pop = new Poblacion(ELITISM_K, POP_SIZE, MAX_ITER, MUTATION_RATE, CROSSOVER_RATE, TOTAL_PREIODOS,
				TOTAL_AULAS, TOTAL_CURSOS, TOTAL_DIAS, TOTAL_ASIGNATURAS_CURSO);
		Horario[] newPop = new Horario[POP_SIZE];
		Horario[] indiv = new Horario[2];
		Horario[] newIndiv = new Horario[2];

		// current population
		System.out.println("Iteration: 0");
		System.out.print("Total Fitness = " + pop.gettotalFirness());
		System.out.print(" ; Best Fitness = " + pop.findBestIndividual().getFitness());
		System.out.println(" ; Mean Fitness = " + pop.gettotalFirness() / POP_SIZE);
		System.out.println("Best Individual: ");
//		pop.findBestIndividual().imprimirDias();

		int count;
		int iter = 0;
//		while(pop.findBestIndividual().getFitness()<1995) {
		for (iter = 0; iter < MAX_ITER; iter++) {
			count = 0;
			newPop = new Horario[POP_SIZE];
			Horario[] sortedPop = new Horario[POP_SIZE];

			// Elitismo
			if (ELITISM_K >= 1) {

				for (int i = 0; i < POP_SIZE; i++) {
					sortedPop[i] = pop.getPoblacion()[i];
				}
				Arrays.sort(sortedPop, Comparator.comparingDouble(Horario::getFitness).reversed());

				// Seleccionar los primeros "ELITISM_K" individuos (los mejores)
				for (int i = 0; i < ELITISM_K; i++) {
					newPop[count] = sortedPop[i];
					count++;
				}
			}

			while (count < POP_SIZE) {

				// seleccion

				switch (ALGORITMO_SELECCION) {
				case 0: {
					indiv[0] = pop.rouletteWheelSelection();
					indiv[1] = pop.rouletteWheelSelection();
					break;
				}
				case 1: {
					indiv[0] = pop.rankSelection();
					indiv[1] = pop.rankSelection();
					break;
				}
				case 2: {
					indiv[0] = pop.seleccionTorneo();
					indiv[1] = pop.seleccionTorneo();
					break;
				}
				case 3: {
					indiv[0] = pop.seleccionTruncada();
					indiv[1] = pop.seleccionTruncada();
					break;
				}
				case 4:{
					indiv[0] = pop.rouletteWheelSelection2();
					indiv[1] = pop.rouletteWheelSelection2();
					break;
				}
					

				default:
					System.out.println("El metodo de seleccion elegido no es correcto");
					break;
				}

				// Cruce

				switch (ESTRATEGIA_REMPLAZAMIENTO) {
				case 1:
					if (pop.getm_rand() < CROSSOVER_RATE) {
						switch (OPERADOR_CRUCE) {
						case 0: {
							indiv = pop.crossover_unPunto(indiv[0], indiv[1]);
							if (pop.getm_rand() < MUTATION_RATE) {
								mutacion(indiv[0], OPERADOT_MUTATION);
							}
							if (pop.getm_rand() < MUTATION_RATE) {
								mutacion(indiv[1], OPERADOT_MUTATION);
							}
							break;
						}
						case 1: {
							indiv = pop.crossover_dosPuntos(indiv[0], indiv[1]);
							if (pop.getm_rand() < MUTATION_RATE) {
								mutacion(indiv[0], OPERADOT_MUTATION);
							}
							if (pop.getm_rand() < MUTATION_RATE) {
								mutacion(indiv[1], OPERADOT_MUTATION);
							}
							break;
						}
						case 2: {
							indiv = pop.crossover_uniforme(indiv[0], indiv[1]);
							if (pop.getm_rand() < MUTATION_RATE) {
								mutacion(indiv[0], OPERADOT_MUTATION);
							}
							if (pop.getm_rand() < MUTATION_RATE) {
								mutacion(indiv[1], OPERADOT_MUTATION);
							}
							break;
						}

						default:
							System.out.println("El metodo de CURCE elegido no es correcto");
							break;
						}
					}
					newPop[count] = indiv[0];
					newPop[count + 1] = indiv[1];
					count += 2;
					break;
				case 0:
					switch (OPERADOR_CRUCE) {
					case 0: {
						newIndiv = pop.crossover_unPunto(indiv[0], indiv[1]);
						if (pop.getm_rand() < MUTATION_RATE) {
							mutacion(newIndiv[0], OPERADOT_MUTATION);
						}
						if (pop.getm_rand() < MUTATION_RATE) {
							mutacion(newIndiv[1], OPERADOT_MUTATION);
						}
						break;
					}
					case 1: {
						newIndiv = pop.crossover_dosPuntos(indiv[0], indiv[1]);
						if (pop.getm_rand() < MUTATION_RATE) {
							mutacion(newIndiv[0], OPERADOT_MUTATION);
						}
						if (pop.getm_rand() < MUTATION_RATE) {
							mutacion(newIndiv[1], OPERADOT_MUTATION);
						}
						break;
					}
					case 2: {
						newIndiv = pop.crossover_uniforme(indiv[0], indiv[1]);
						if (pop.getm_rand() < MUTATION_RATE) {
							mutacion(newIndiv[0], OPERADOT_MUTATION);
						}
						if (pop.getm_rand() < MUTATION_RATE) {
							mutacion(newIndiv[1], OPERADOT_MUTATION);
						}
						break;
					}

					default:
						System.out.println("El metodo de CURCE elegido no es correcto");
						break;
					}
					Random rd = new Random();
					newPop[count] = indiv[rd.nextInt(2)];
					newPop[count + 1] = newIndiv[rd.nextInt(2)];
					count += 2;
					break;
				default:
					System.out.println("El metodo de REMPLAZAMIENTO elegido no es correcto");
					break;
				}
			}

		
		pop.setPoblacion(newPop);

		// reevaluate current population

		/*
		 * Se evalua la nueva población, ya que es necesario para la siguiente
		 * selección por ruleta.
		 */
		pop.evaluate();
		System.out.println("Iteration: " + iter);
		System.out.print("Total Fitness = " + pop.gettotalFirness());
		System.out.print(" ; Best Fitness = " + pop.findBestIndividual().getFitness());
		System.out.println(" ; Mean Fitness = " + pop.gettotalFirness() / POP_SIZE);
//
		System.out.println("Best Individual: ");
//			pop.findBestIndividual().imprimirDias();
		if (iter == MAX_ITER - 1) {
//				pop.findBestIndividual().imprimirHorario2();
//				pop.findBestIndividual().imprimirHorario3();
			pop.findBestIndividual().imprimirHorarioMixto();
		}
	}
	}
	
	private static void mutacion(Horario horario, int tipo_cruce) {

		switch (tipo_cruce) {
		case 0: {
			horario.mutacion_adhoc(horario);
			break;
		}
		case 1: {
			horario.mutacion_adhoc2(horario);
			break;
		}

		default:
			System.out.println("El metodo de MUTACION elegido no es correcto");
			break;
		}
	}

}
