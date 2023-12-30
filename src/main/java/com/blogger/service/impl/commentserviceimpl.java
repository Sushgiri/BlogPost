package com.blogger.service.impl;

import com.blogger.entity.Post;
import com.blogger.entity.comment;
import com.blogger.exception.ResourceNotFoundExcecption;
import com.blogger.payload.commentdto;
import com.blogger.repository.CommentRepository;
import com.blogger.repository.PostRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Component
public class commentserviceimpl {

    private PostRepository postrepo;

    private CommentRepository commentrepo;

    public commentserviceimpl(PostRepository postrepo, CommentRepository commentrepo) {
        this.postrepo = postrepo;
        this.commentrepo = commentrepo;
    }

    public commentdto save(long postId, commentdto commdto) {
        Post post = postrepo.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundExcecption("post not found with id:"+postId)
        );
                comment comment = new comment();
                   comment.setId(commdto.getId());
                   comment.setName(commdto.getName());
                   comment.setBody(commdto.getBody());
                   comment.setEmail(commdto.getEmail());
                   comment.setRating(commdto.getRating());
                   comment.setPost(post);

                   com.blogger.entity.comment cdtos = commentrepo.save(comment);

                   commentdto returdto = new commentdto();
                   returdto.setId(cdtos.getId());
                   returdto.setName(cdtos.getName());
                   returdto.setBody(cdtos.getBody());
                   returdto.setEmail(cdtos.getEmail());

                   return  returdto;
    }

    public void deletecomment(long id){
         commentrepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundExcecption("No Comment with id is present:"+id)
        );
        commentrepo.deleteById(id);
    }
    public List<commentdto> getcommentbypostid(long id){
        List<comment> comments = commentrepo.findBypostId(id);


        List<commentdto> cdtos = comments.stream().map(p->mapTodto(p)).collect(Collectors.toList());

        return cdtos;
    }


    commentdto mapTodto(comment comme){
        commentdto cdto = new commentdto();
        cdto.setId(comme.getId());
        cdto.setName(comme.getName());
        cdto.setBody(comme.getBody());
        cdto.setEmail(comme.getEmail());
        cdto.setRating(comme.getRating());


        return cdto;

    }

    public Post mostrated() {

        comment comm = new comment();
        List<comment> comments = commentrepo.findAll();

        comment posts  = comments.stream().max(comment::compareTo).get();
        Post pos = posts.getPost();
        Post fetched = new Post();
        fetched.setId(pos.getId());
        fetched.setTitle(pos.getTitle());
        fetched.setDescription(pos.getDescription());
        fetched.setContent(pos.getContent());

        return fetched;
    }
    public Post leastrated() {

        comment comm = new comment();
        List<comment> comments = commentrepo.findAll();

        comment posts  = comments.stream().min(comment::compareTo).get();
        Post pos = posts.getPost();
        Post fetched = new Post();
        fetched.setId(pos.getId());
        fetched.setTitle(pos.getTitle());
        fetched.setDescription(pos.getDescription());
        fetched.setContent(pos.getContent());

        return fetched;
    }

    public void update(long commentid, commentdto dto) {

        comment comment = commentrepo.findById(commentid).orElseThrow(
                () -> new ResourceNotFoundExcecption("There is No Comment with id " + commentid)
        );
          comment.setId(dto.getId());
          comment.setName(dto.getName());
          comment.setBody(dto.getBody());
          comment.setRating(dto.getRating());
          comment.setEmail(dto.getEmail());

          commentrepo.save(comment);

    }


    public List<comment> getallcomments(){
        List<comment> all = commentrepo.findAll();
        return all;
    }


}
