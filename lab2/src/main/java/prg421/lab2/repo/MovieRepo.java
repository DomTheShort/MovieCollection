package prg421.lab2.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import prg421.lab2.model.Movie;

import java.util.List;

/**
 * Created by Dominic on 10/25/2016.
 */
public interface MovieRepo extends CrudRepository<Movie,Integer>
{
    List<Movie> findByTitle(@Param("title") String title);
    List<Movie> findByTitleLike(@Param("title") String title);
}
