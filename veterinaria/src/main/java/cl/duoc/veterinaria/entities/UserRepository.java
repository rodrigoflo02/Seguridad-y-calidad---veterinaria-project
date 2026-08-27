package cl.duoc.veterinaria.entities;


import org.springframework.data.repository.CrudRepository;
import cl.duoc.veterinaria.entities.User;

// Este código será CREADO AUTOMATICAMENTE por Spring en un Bean llamado userRepository
// CRUD significa Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);

}