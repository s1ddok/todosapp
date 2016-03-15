package sample.todosapp.spring.controller;

import java.util.concurrent.Callable;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sample.todosapp.spring.dao.UserDao;
import sample.todosapp.spring.dao.UserDaoImpl;
import sample.todosapp.spring.domain.Todo;
import sample.todosapp.spring.domain.User;
import sample.todosapp.spring.service.TodoRepository;
import sample.todosapp.error.ErrorMessages;
import sample.todosapp.error.ErrorMessages.Message;
import sample.todosapp.spring.service.UserService;

/**
 * To-do REST web service. The CRUD operations are executed asynchronously using
 * Spring MVC.
 */
@RestController
@RequestMapping("/api/todos")
public class TodoRestController {

    /** The application service for CRUD operations on Todo entities */
    @Autowired TodoRepository todoRepository;
    @Autowired UserService userService;

    @RequestMapping(method = GET)
    public Callable<Iterable<Todo>> getAll() {
        User u = userService.findBySso(SecurityContextHolder.getContext().getAuthentication().getName());

        return () -> todoRepository.findByUser(u);
    }

    @RequestMapping(method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Callable<Todo> post(@RequestBody Todo todo) {
        User u = userService.findBySso(SecurityContextHolder.getContext().getAuthentication().getName());

        todo.setUser(u);
        return () -> todoRepository.save(todo);
    }

    @RequestMapping(value = "/{id}", method = GET)
    public Callable<Todo> get(@PathVariable("id") Long id) {
        return () -> {
            Todo result = todoRepository.findOne(id);
            if (result == null) {
                throw new EntityNotFoundException();
            }
            return result;
        };
    }

    @RequestMapping(value = "/{id}", method = PUT)
    @ResponseStatus(value=HttpStatus.OK)
    public Callable<Todo> put(@RequestBody Todo todo) {
        return () -> todoRepository.save(todo);
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Callable<Void> delete(@PathVariable("id") final Long id) {
        return () -> {
            todoRepository.delete(id);
            return null;
        };
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    public Message conflict() {
        return ErrorMessages.CONFLICT_ERR;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)  // 404
    public Message notFound() {
        return ErrorMessages.NOT_FOUND_ERR;
    }
}
