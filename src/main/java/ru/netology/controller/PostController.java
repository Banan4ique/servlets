package ru.netology.controller;

import com.google.gson.Gson;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    public void all(HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var data = service.all();
        final var gson = new Gson();
        response.getWriter().print(gson.toJson(data));
    }

    public void getById(long id, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        try {
            final var data = service.getById(id);
            response.getWriter().print(gson.toJson(data));
        } catch (NotFoundException e) {
            response.getWriter().print("Content with id=" + id + " not found");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        final var post = gson.fromJson(body, Post.class);
        try {
            final var data = service.save(post);
            response.getWriter().print(gson.toJson(data));
        } catch (NotFoundException e) {
            response.getWriter().print("Content with id=" + post.getId() + " not found");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    public void removeById(long id, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        try {
            service.removeById(id);
            response.getWriter().print("Content with id=" + id + " removed");
        } catch (NotFoundException e) {
            response.getWriter().print("Content with id=" + id + " not found");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
