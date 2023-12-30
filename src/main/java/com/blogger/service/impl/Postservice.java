package com.blogger.service.impl;

import com.blogger.entity.Post;
import com.blogger.payload.PostDto;

import java.util.List;
import java.util.Optional;

public interface Postservice {

    public PostDto createPost(PostDto postDto);


    void deletepost(long id);

      void updatesave(Post post);
      Optional<Post> find(long id);


    List<Post> readAll();



}
