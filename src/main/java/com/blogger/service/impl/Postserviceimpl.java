package com.blogger.service.impl;

import com.blogger.entity.Post;
import com.blogger.exception.ResourceNotFoundExcecption;
import com.blogger.payload.PostDto;
import com.blogger.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Postserviceimpl implements Postservice {

    private PostRepository postrepo;

    public Postserviceimpl(PostRepository postrepo) {
        this.postrepo = postrepo;
    }
    //phase 1
//    @Override
//    public PostDto createPost(PostDto postDto) {
//
//       Post post = new Post();
//       post.setTitle(postDto.getTitle());
//       post.setDescription(postDto.getDescription());
//       post.setContent(postDto.getContent());
//        postrepo.save(post);
//        return postDto;
//    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post savedPost = postrepo.save(post);

        PostDto dto = new PostDto();
        dto.setId(savedPost.getId());
        dto.setTitle(savedPost.getTitle());
        dto.setDescription(savedPost.getDescription());
        dto.setContent(savedPost.getContent());
        dto.setMessage("post is Created");
      
        return dto;
    }

//    @Override
//    public void deletepost(long id) {
//        Optional<Post> byId = postrepo.findById(id);
//        if(byId.isPresent()) {
//            postrepo.deleteById(id);
//        }
//        else{
//            throw new ResourceNotFoundExcecption("Post not Found With id:"+id);
//        }
//    }

    @Override
    public void deletepost(long id) {


    Post post = postrepo.findById(id).orElseThrow(
            () -> new ResourceNotFoundExcecption("post is not found with id:" + id)

    );
    postrepo.deleteById(id);
}

@Override
public Optional<Post> find(long id){
     Optional<Post> byId = postrepo.findById(id);
    return byId;
}

    @Override
    public List<Post> readAll() {
        List<Post> all = postrepo.findAll();


        return all;
    }


    @Override
    public void updatesave(Post post)
    {
          postrepo.save(post);

  }




//    @Override
//    public List<Post> readAll() {
//        return postrepo.findAll();
//    }


}