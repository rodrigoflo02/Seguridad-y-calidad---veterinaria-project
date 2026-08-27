package cl.duoc.veterinaria.Controlller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cl.duoc.veterinaria.model.Cita;
import cl.duoc.veterinaria.model.Paciente;

@Controller
public class HomeController {

    private final List<Paciente> listaPacientes = new ArrayList<>();
    private final List<Cita> listaCitas = new ArrayList<>();

    @GetMapping("/home")
    public String home(@RequestParam(name = "name", required = false, defaultValue = "Veterinaria DuocUC") String name,
            Model model) {
        model.addAttribute("name", name);
        return "Home";
    }

    @GetMapping("/")
    public String root(@RequestParam(name = "name", required = false, defaultValue = "Veterinaria DuocUC") String name,
            Model model) {
        model.addAttribute("name", name);
        return "Home";
    }

    @GetMapping("/login")
    public String login(@RequestParam(name = "name", required = false, defaultValue = "Veterinaria DuocUC") String name,
            Model model) {
        model.addAttribute("name", name);
        return "login";
    }


    // Ruta Privada 1: Registro y listado de Pacientes
    @GetMapping("/pacientes")
    public String pacientes(Model model) {
        model.addAttribute("pacientes", listaPacientes);
        return "pacientes";
    }

    @PostMapping("/pacientes")
    public String guardarPaciente(
            @RequestParam String nombre,
            @RequestParam String especie,
            @RequestParam int edad,
            @RequestParam String dueño) {
        listaPacientes.add(new Paciente(nombre, especie, edad, dueño));
        return "redirect:/pacientes";
    }

    // Ruta Privada 2: Agendamiento y listado de Citas
    @GetMapping("/citas")
    public String citas(Model model) {
        model.addAttribute("citas", listaCitas);
        return "citas";
    }

    @PostMapping("/citas")
    public String guardarCita(
            @RequestParam String fecha,
            @RequestParam String hora,
            @RequestParam String motivo_consulta,
            @RequestParam String veterinario)
        {
        listaCitas.add(new Cita(fecha, hora, motivo_consulta, veterinario));
        return "redirect:/citas";
    }
}