package prg421.lab2.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import prg421.lab2.model.Movie;
import prg421.lab2.model.MovieList;
import prg421.lab2.repo.MovieRepo;

import java.io.IOException;
import java.util.List;

/**
 * Created by Dominic on 10/27/2016.
 */
@Controller
public class MovieFormController {
    @Autowired
    MovieRepo repo;

    //@Autowired
    RestTemplate restTemplate = new RestTemplate();

    ObjectMapper mapper = new ObjectMapper();

    public MovieFormController()
    {
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @RequestMapping("/movie/{id}")
    public String findMovie(@PathVariable("id") int id, Model model)
    {
        Movie m = repo.findOne(id);
        if(m==null)
        {
            m = new Movie();
            model.addAttribute("movie", m);
            return "movieform";
        }
        else
        {
            model.addAttribute("movie", m);
            return "movie";
        }
    }

    @RequestMapping(value = "/movie", method = RequestMethod.POST)
    public String save(@ModelAttribute Movie m, Model model)
    {
        HttpEntity<Movie> request = new HttpEntity<>(m);
        HttpEntity<Movie> resp = restTemplate.exchange("http://localhost:8080/film", HttpMethod.POST, request, Movie.class);
        model.addAttribute("saveMessage", "Movie has been saved.");
        //model.addAttribute("movie", repo.save(m));
        return "movie";
    }

    @RequestMapping("/omdb/{title}")
    public String lookup(@PathVariable("title") String title, Model model)
    {
        String url = "http://www.omdbapi.com/?s=" + title;
        String m = restTemplate.getForObject(url, String.class);
        MovieList mList = null;
        try {
            mList = mapper.readValue(m , MovieList.class);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        model.addAttribute("movielist", mList);

        return "movielist";
    }

    @RequestMapping("omdb/{imdbId}/detail")
    public String getDetails(@PathVariable("imdbId") String id, Model model)
    {
        String url = "http://www.omdbapi.com/?i=" + id;
        String m = restTemplate.getForObject(url, String.class);
        Movie movie = null;
        try {
            movie = mapper.readValue(m, Movie.class);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        model.addAttribute("movie", movie);
        return "movieform";
    }
}
