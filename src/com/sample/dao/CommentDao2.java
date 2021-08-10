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
	 * 全体のデータを取得
	 * 
	 * @return
	 */
	@ResultMap("commentResult4")
	@Select("select comments.id, comments.comment, comments.created_at, comments.user_id, user.user_name from comments inner join user on user.id = comments.user_id where not exists (select * from replies where comments.id = replies.reply_comment_id)")
	List<Comment2> selectAllComments();

	/**
	 * 指定コメントIDに紐づく返信IDを取得
	 * 
	 * @param commentId
	 * @return
	 */
	@ResultMap("commentResult2")
	@Select("select * from replies where comment_id = #{id}")
	List<Comment2> selectReplyIdsByCommentId(int commentId);

	/**
	 * 返信IDを指定してコメントデータを取得
	 * 
	 * @param replyCommentId
	 * @return
	 */
	@ResultMap("commentResult4")
	@Select("select comments.id, comment, created_at, user_id, user_name from comments inner join user where comments.id = #{id} and comments.user_id = user.id")
	List<Comment2> selectCommentByCommentId(int replyCommentId);

	/**
	 * ユーザデータの存在確認
	 * 
	 * @param userName
	 * @return
	 */
	@ResultMap("commentResult1")
	@Select("select * from user where user_name = #{userName}")
	List<Comment2> selectUserByUserName(String userName);

	/**
	 * 最新のコメントIDを取得（かつ今回登録したコメント内容と合致するコメントのIDであること）
	 * 
	 * @return
	 */
	@ResultMap("commentResult3")
	@Select("select * from comments where comment = #{text} and user_id = #{userId} order by created_at desc limit 1")
	Comment2 selectLatestComment(@Param("text") String text, @Param("userId") int userId);
	
	/**
	 * ユーザ名をユーザテーブルに登録
	 * 
	 * @param userName
	 */
	// このメソッドに限ってはSQL文をCommentMapperで定義している
	void insertUserName(String userName);

	/**
	 * コメントをコメントテーブルに登録
	 * 
	 * @param comment
	 * @param userId
	 */
	@Insert("insert into comments (comment, user_id) values (#{text}, #{userId})")
	void insertComment(String comment, int userId);

	/**
	 * 返信コメントIDを返信テーブルに登録
	 * 
	 * @param commentId
	 * @param replyCommentId
	 */
	@Insert("insert into replies (comment_id, reply_comment_id) values (#{id}, #{replyId})")
	void insertReplyCommentId(int commentId, int replyCommentId);
}
