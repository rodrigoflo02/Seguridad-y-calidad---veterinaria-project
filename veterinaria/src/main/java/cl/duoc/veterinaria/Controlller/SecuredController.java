package cl.duoc.veterinaria.Controlller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecuredController {

    // Ejemplo de ruta privada de la guía. Solo se ejecuta si el filtro JWT
    // cargó una autenticación válida en SecurityContextHolder.
    @RequestMapping("greetings")
    public String greetings(@RequestParam(value="name", defaultValue="World") String name) {
        return "Hello {" + name + "}";
    }
}
