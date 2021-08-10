package com.sample.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.sample.Comment;
import com.sample.Comment2;

public interface CommentDao2 {

	/**
	 * �S�̂̃f�[�^���擾
	 * 
	 * @return
	 */
	@ResultMap("commentResult4")
	@Select("select comments.id, comments.comment, comments.created_at, comments.user_id, user.user_name from comments inner join user on user.id = comments.user_id where not exists (select * from replies where comments.id = replies.reply_comment_id)")
	List<Comment2> selectAllComments();

	/**
	 * �w��R�����gID�ɕR�Â��ԐMID���擾
	 * 
	 * @param commentId
	 * @return
	 */
	@ResultMap("commentResult2")
	@Select("select * from replies where comment_id = #{id}")
	List<Comment2> selectReplyIdsByCommentId(int commentId);

	/**
	 * �ԐMID���w�肵�ăR�����g�f�[�^���擾
	 * 
	 * @param replyCommentId
	 * @return
	 */
	@ResultMap("commentResult4")
	@Select("select comments.id, comment, created_at, user_id, user_name from comments inner join user where comments.id = #{id} and comments.user_id = user.id")
	List<Comment2> selectCommentByCommentId(int replyCommentId);

	/**
	 * ���[�U�f�[�^�̑��݊m�F
	 * 
	 * @param userName
	 * @return
	 */
	@ResultMap("commentResult1")
	@Select("select * from user where user_name = #{userName}")
	List<Comment2> selectUserByUserName(String userName);

	/**
	 * �ŐV�̃R�����gID���擾�i������o�^�����R�����g���e�ƍ��v����R�����g��ID�ł��邱�Ɓj
	 * 
	 * @return
	 */
	@ResultMap("commentResult3")
	@Select("select * from comments where comment = #{text} and user_id = #{userId} order by created_at desc limit 1")
	Comment2 selectLatestComment(@Param("text") String text, @Param("userId") int userId);
	
	/**
	 * ���[�U�������[�U�e�[�u���ɓo�^
	 * 
	 * @param userName
	 */
	// ���̃��\�b�h�Ɍ����Ă�SQL����CommentMapper�Œ�`���Ă���
	void insertUserName(String userName);

	/**
	 * �R�����g���R�����g�e�[�u���ɓo�^
	 * 
	 * @param comment
	 * @param userId
	 */
	@Insert("insert into comments (comment, user_id) values (#{text}, #{userId})")
	void insertComment(String comment, int userId);

	/**
	 * �ԐM�R�����gID��ԐM�e�[�u���ɓo�^
	 * 
	 * @param commentId
	 * @param replyCommentId
	 */
	@Insert("insert into replies (comment_id, reply_comment_id) values (#{id}, #{replyId})")
	void insertReplyCommentId(int commentId, int replyCommentId);
}
