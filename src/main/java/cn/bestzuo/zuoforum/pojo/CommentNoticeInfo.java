package cn.bestzuo.zuoforum.pojo;

import lombok.Data;

@Data
public class CommentNoticeInfo {
    private Integer id;

    private Integer parentCommentId;

    private Integer commentId;

    private String commentName;

    private Integer noticeId;

    private String noticeName;

    private String time;

    private Integer questionId;

    private Integer status;

    private String content;
}