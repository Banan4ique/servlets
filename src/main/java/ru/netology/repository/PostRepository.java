package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Stub
public class PostRepository {

  private AtomicLong idCounter = new AtomicLong(0);
  private ConcurrentHashMap<Long, Post> map = new ConcurrentHashMap<>();

  public List<Post> all() {
    return map.values().stream().toList();
  }

  public Optional<Post> getById(long id) {
      return Optional.ofNullable(map.get(id));
  }

  public Post save(Post post) {
    if (post.getId() == 0) {
      post.setId(idCounter.incrementAndGet());
      map.put(post.getId(), post);
    } else {
      if (map.containsKey(post.getId())) {
        map.replace(post.getId(), post);
      } else {
        throw new NotFoundException("Элемент с таким id не найден");
      }
    }
    return post;
  }

  public void removeById(long id) {
    if (!map.containsKey(id)) {
      throw new NotFoundException("Элемент с таким id не найден");
    }
    map.remove(id);
  }
}
