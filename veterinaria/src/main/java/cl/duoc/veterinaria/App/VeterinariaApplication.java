package cl.duoc.veterinaria.App;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Los componentes de seguridad, JWT y controladores están en paquetes hermanos
// de App, por lo que el escaneo debe comenzar en la raíz común.
@SpringBootApplication(scanBasePackages = "cl.duoc.veterinaria")
public class VeterinariaApplication {

	public static void main(String[] args) {
		SpringApplication.run(VeterinariaApplication.class, args);
	}

}
