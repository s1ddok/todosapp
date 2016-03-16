package sample.todosapp.spring.service;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import sample.todosapp.spring.domain.Todo;
import sample.todosapp.spring.domain.User;


/**
 * The repository for to-do items, providing standard CRUD operations as well
 * as a custom query method.
 */
public interface TodoService extends CrudRepository<Todo,Long>{
	
    /**
     * Finds the to-do items based on its completion status. This is a 
     * custom query method.
     * 
     * @param completed whether or not the items are completed
     * @return a list of to-do items
     */
	List<Todo> findByCompleted(boolean completed);

    List<Todo> findByUser(User user);
}
