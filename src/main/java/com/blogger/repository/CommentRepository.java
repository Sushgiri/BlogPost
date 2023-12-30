package com.blogger.repository;

import com.blogger.entity.comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentRepository extends JpaRepository<comment,Long> {
    List<comment> findBypostId(long   postId);
}
