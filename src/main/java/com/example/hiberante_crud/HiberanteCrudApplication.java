package com.example.hiberante_crud;

import com.example.hiberante_crud.models.dao.NadadorDaO;
import com.example.hiberante_crud.models.dao.NadadorDaOImpl;
import com.example.hiberante_crud.models.entity.Nadador;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class HiberanteCrudApplication {
	public static void main(String[] args) {
		SpringApplication.run(HiberanteCrudApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(NadadorDaOImpl nadadorDAOImpl) {
		return runner -> {

			Scanner scanner = new Scanner(System.in);

			int opcion;
			do {
				mostrarMenu();
				opcion = scanner.nextInt();
				scanner.nextLine();
				switch (opcion) {
					case 1:
						createNadador(nadadorDAOImpl);
						break;
					case 2:
						buscarNadadorPorId(nadadorDAOImpl);
						break;
					case 3:
						buscarPorEmail(nadadorDAOImpl);
						break;
					case 4:
						buscarTodo(nadadorDAOImpl);
						break;
					case 5:
						updateNadador(nadadorDAOImpl);
						break;
					case 6:
						borrarNadadorPorId(nadadorDAOImpl);
						break;
					case 9:
						System.out.println("Saliendo del programa. ¡Hasta luego!");
						break;
					case 143:
						hacerLimpieza(nadadorDAOImpl);
						break;
					default:
						System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
				}
			} while (opcion != 9);
		};
	}

	private static void mostrarMenu() {
		System.out.println("======= Menú =======");
		System.out.println("1. Crear Nadador");
		System.out.println("2. Buscar Nadador por ID");
		System.out.println("3. Buscar Nadador por Email");
		System.out.println("4. Mostrar Todos los Nadadores");
		System.out.println("5. Modificar datos de un Nadador");
		System.out.println("6. Eliminar un nadador por ID");
		System.out.println("9. Salir");
		System.out.println("143. Purgar tabla");
		System.out.print("Seleccione una opción: ");
	}

	private void createNadador(NadadorDaOImpl nadadorDaO) {
		Scanner scanner = new Scanner(System.in);

		Nadador nadador = new Nadador();

		System.out.print("Ingrese el Nombre: ");
		String nombre = scanner.nextLine();
		nadador.setNombre((nombre != null && !nombre.isEmpty()) ? nombre : "John");

		System.out.print("Ingrese el Apellido: ");
		String apellido = scanner.nextLine();
		nadador.setApellido((apellido != null && !apellido.isEmpty()) ? apellido : "Doe");

		System.out.print("Ingrese el País: ");
		String pais = scanner.nextLine();
		nadador.setPais((pais != null && !pais.isEmpty()) ? pais : "USA");

		System.out.print("Ingrese la Edad: ");
		String edadInput = scanner.nextLine();
		nadador.setEdad((edadInput != null && !edadInput.isEmpty()) ? Integer.parseInt(edadInput) : 25);

		System.out.print("Ingrese el Email: ");
		String email = scanner.nextLine();
		nadador.setEmail((email != null && !email.isEmpty()) ? email : "john.doe@email.something");

		System.out.print("Ingrese el Número Federado: ");
		String numeroFederado = scanner.nextLine();
		nadador.setNumeroFederado((numeroFederado != null && !numeroFederado.isEmpty()) ? numeroFederado : "123456");

		System.out.println(nadador.getApellido() + ", " + nadador.getNombre()+" se ha guardado en la base de datos");
		nadadorDaO.saveNadador(nadador);
	}

	private void buscarNadadorPorId(NadadorDaO nadadorDao) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Ingrese el ID del Nadador a buscar: ");
		int idNadador = scanner.nextInt();

		Nadador nadador = nadadorDao.findById(idNadador);
		if (nadador != null) {
			listarDatosNadador(nadador);
		} else {
			System.out.println("No se encontró ningún Nadador con el ID proporcionado.");
		}
	}

	private void buscarPorEmail(NadadorDaO nadadorDao) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Ingrese el email del nadador: ");
		String emailNadador = scanner.next();

		List<Nadador> nadadoresEncontrados = nadadorDao.findByEmail(emailNadador);

		if (!nadadoresEncontrados.isEmpty()) {
			System.out.println("Nadadores encontrados por email:");
			for (Nadador nadador : nadadoresEncontrados) {
				HiberanteCrudApplication.listarDatosNadador(nadador);
				System.out.println("--------------------");
			}
		} else {
			System.out.println("No se encontraron nadadores con el correo proporcionado.");
		}
	}

	public static void listarDatosNadador(Nadador nadador) {
		System.out.println("ID: " + nadador.getId());
		System.out.println("Nombre: " + nadador.getNombre());
		System.out.println("Apellido: " + nadador.getApellido());
		System.out.println("Email: " + nadador.getEmail());
		System.out.println("País: " + nadador.getPais());
		System.out.println("Edad: " + nadador.getEdad());
		System.out.println("Número Federado: " + nadador.getNumeroFederado());
	}

	private void buscarTodo(NadadorDaO nadadorDao) {
		System.out.println("Buscando todos los nadadores:");
		nadadorDao.buscarTodo();
	}

	private void updateNadador(NadadorDaO nadadorDaO) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Ingrese el ID del Nadador a buscar: ");
		int idNadador = scanner.nextInt();

		Nadador nadador = nadadorDaO.findById(idNadador);
		if (nadador != null) {
			boolean salir = false;
			do {
				mostrarMenuUpdate();
				int opcion = scanner.nextInt();
				scanner.nextLine();

				switch (opcion) {
					case 1:
						System.out.println("Nombre actual: "+nadador.getNombre());
						System.out.println("Nuevo nombre: ");
						nadador.setNombre(scanner.nextLine());
						break;
					case 2:
						System.out.println("Apellido actual: "+nadador.getApellido());
						System.out.println("Nuevo apellido: ");
						nadador.setApellido(scanner.nextLine());
						break;
					case 3:
						System.out.println("Emilio actual: "+nadador.getEmail());
						System.out.println("Nuevo emilio: ");
						nadador.setEmail(scanner.nextLine());
						break;
					case 4:
						System.out.println("Pais actual: "+nadador.getPais());
						System.out.println("Nuevo país: ");
						nadador.setPais(scanner.nextLine());
						break;
					case 5:
						System.out.println("Edad actual: "+nadador.getEdad());
						System.out.println("Nueva edad: ");
						nadador.setEdad(scanner.nextInt());
						break;
					case 6:
						System.out.println("Número de federado actual: "+nadador.getNumeroFederado());
						System.out.println("Nuevo número de federado: ");
						nadador.setNumeroFederado(scanner.nextLine());
						break;
					case 33:
						nadadorDaO.updateNadador(nadador);
						System.out.println("Nadador actualizado exitosamente.");
						salir = true;
						break;
					case 77:
						System.out.println("Cambios descartados. Saliendo del menú de modificación.");
						salir = true;
						break;
					default:
						System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
				}
			} while (!salir);
		} else {
			System.out.println("No se encontró ningún Nadador con el ID proporcionado.");
		}
	}
	private void mostrarMenuUpdate() {
		System.out.println("======= Menú de Actualización =======");
		System.out.println("1. Actualizar Nombre");
		System.out.println("2. Actualizar Apellido");
		System.out.println("3. Actualizar Email");
		System.out.println("4. Actualizar País");
		System.out.println("5. Actualizar Edad");
		System.out.println("6. Actualizar Número Federado");
		System.out.println("33. Guardar Cambios");
		System.out.println("77. Descartar Cambios");
		System.out.print("Seleccione una opción: ");
	}

	private void borrarNadadorPorId(NadadorDaO nadadorDao) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Ingrese el ID del Nadador que desea eliminar: ");
		int idNadador = scanner.nextInt();

		Nadador nadador = nadadorDao.findById(idNadador);
		if (nadador != null) {
			try {
				nadadorDao.deleteNadador(Math.toIntExact(nadador.getId()));
				System.out.println("Nadador eliminado satisfactoriamente.");
			} catch (Exception e) {
				System.out.println("Error al intentar eliminar el Nadador. Detalles: " + e.getMessage());
			}
		} else {
			System.out.println("No se encontró ningún Nadador con el ID proporcionado.");
		}
	}

	private void hacerLimpieza(NadadorDaO nadadorDaO){
		nadadorDaO.deleteAll();
	}
}
