package prg421.lab2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import prg421.lab2.model.Movie;
import prg421.lab2.repo.MovieRepo;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by Dominic on 10/25/2016.
 */
@RestController
public class MovieController
{
    @Autowired
    MovieRepo movieRepo;

    @PostConstruct
    public void init()
    {
        for (int i = 0; i < 50; i++)
        {
            Movie m = new Movie();
            m.setTitle("Big Hero " + i);
            m.setImdbRating("10/10");
            m.setReleased("Jan 17, 2014");
            movieRepo.save(m);
        }
    }

    /*@RequestMapping(value = "/movie/{id}", method = RequestMethod.GET)
    public String getMovie(@PathVariable("id") int id)
    {
        Movie m = movieRepo.findOne(id);
        return m.getTitle();
    }*/

    @RequestMapping(value = "/movie", method = RequestMethod.GET)
    public Iterable<Movie> getAllMovies()
    {
        return movieRepo.findAll();
    }

    @RequestMapping("/movie/like/{title}")
    public List<Movie> findByTitle(@PathVariable("title") String title)
    {
        return movieRepo.findByTitleLike("%" + title + "%");
    }

    @RequestMapping("/film")
    public void addMovie(@RequestBody(required = true) Movie movie)
    {
        movieRepo.save(movie);
    }
}
