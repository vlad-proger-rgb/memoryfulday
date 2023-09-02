package com.vladitproger.memoryfulday.repo;

import com.vladitproger.memoryfulday.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
