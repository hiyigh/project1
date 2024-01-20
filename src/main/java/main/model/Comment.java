package main.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class Comment extends Time {
    private Long commentId;
    private Long userId;
    private Long postId;
    private Long mainCommentId;
    private String commentContent;
    private List<Comment> subCommentList;
    private boolean isRoot;
    private boolean isSecret;

    public Comment(Comment comment) {
        this.commentId = comment.getCommentId();
        this.userId = comment.getUserId();
        this.postId = comment.getMainCommentId();
        this.commentContent = comment.getCommentContent();
        this.subCommentList = comment.getSubCommentList();
        this.isRoot = comment.isRoot();
        this.isSecret = comment.isSecret();
    }
    @Builder
    public void builder(Long commentId, Long userId, Long postId, String commentContent, Long mainCommentId, boolean isSecret, boolean isRoot) {
        this.commentId = commentId;
        this.userId = userId;
        this.postId = postId;
        this.commentContent = commentContent;
        this.isSecret = isSecret;
        this.mainCommentId = mainCommentId;
        this.isRoot = isRoot;
    }

    public static void recursiveCommentList(List<Comment> source, Comment root) {
        for (int i = 0; i < source.size(); i++) {
            Comment comment = source.get(i);

            if (comment.isRoot) {
                root.subCommentList.add(new Comment(comment));
                source.remove(i);

                int idx = root.subCommentList.size() - 1;
                recursiveCommentList(source, root.subCommentList.get(idx));

                i--;
            } else {
                for (int j = source.size() - 1; j >= 0; j--) {
                    Comment subComment = source.get(j);

                    if (root.commentId.equals(subComment.mainCommentId)) {
                        root.subCommentList.add(new Comment(subComment));
                        source.remove(j);

                        int idx = root.subCommentList.size() - 1;
                        recursiveCommentList(source, root.subCommentList.get(idx));
                    }
                }
            }
        }
    }
}
