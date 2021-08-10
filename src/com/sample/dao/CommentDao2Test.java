package com.sample.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.sample.Comment;
import com.sample.Comment2;

public class CommentDao2Test {

	static void execute() {
		System.out.println("CommentDao2Test");
//		selectAllCommentsTest();
//		selectCommentByIdTest();
//		selectCommentByCommentIdTest();
//		selectUserByUserNameTest("山田　和夫");
//		selectLatestCommentTest();
		insertUserNameTest();
	}

	static SqlSession openSqlSession() {
		String resource = "config.xml";
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);

			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

			SqlSession session = sqlSessionFactory.openSession();
			return session;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	static void commitAndCloseSqlSession(SqlSession session) {
		session.commit();
		session.close();
	}

	static void closeSqlSession(SqlSession session) {
		session.close();
	}

	/**
	 * 全体のデータを取得
	 * 
	 * @return
	 */
//	@ResultMap("commentResult4")
//	@Select("select comments.id, comments.comment, comments.created_at, comments.user_id, user.user_name from comments inner join user on user.id = comments.user_id where not exists (select * from replies where comments.id = replies.reply_comment_id)")
	static void selectAllCommentsTest() {
		System.out.println("selectAllCommentsTest");
		
		SqlSession session = openSqlSession();

		CommentDao2 dao = session.getMapper(CommentDao2.class);
		List<Comment2> list = dao.selectAllComments();
		for (Comment2 c : list) {
			System.out.println("id: " + c.getId());
			System.out.println("userName: " + c.getUserName());
			System.out.println("comment: " + c.getComment());
			System.out.println("createdAt: " + c.getCreatedAt());
			System.out.println("userId: " + c.getUserId());
			System.out.println("-------------------");
		}
		closeSqlSession(session);
	}

	/**
	 * 指定コメントIDに紐づく返信IDを取得
	 * 
	 * @param commentId
	 * @return
	 */
//	@ResultMap("commentResult2")
//	@Select("select * from replies where comment_id = #{id}")
	static void selectReplyIdsByCommentIdTest() {
		System.out.println("selectReplyIdsByCommentIdTest");
		
		SqlSession session = openSqlSession();

		CommentDao2 dao = session.getMapper(CommentDao2.class);
		List<Comment2> list = dao.selectReplyIdsByCommentId(1);
		for (Comment2 c : list) {
			System.out.println("id: " + c.getId());
			System.out.println("replyId: " + c.getReplyId());
			System.out.println("-------------------");
		}

		closeSqlSession(session);
	}

	// TODO 下記のメソッド名は ...ReplyId ではなく、...CommentId でよい？

	/**
	 * 返信IDを指定してコメントデータを取得
	 * 
	 * @param replyCommentId
	 * @return
	 */
//	@ResultMap("commentResult4")
//	@Select("select comments.id, comment, created_at, user_id, user_name from comments inner join user where comments.id = #{id} and comments.user_id = user.id")
	static void selectCommentByCommentIdTest() {
		System.out.println("selectCommentByCommentIdTest");
		
		SqlSession session = openSqlSession();

		CommentDao2 dao = session.getMapper(CommentDao2.class);
		List<Comment2> list = dao.selectCommentByCommentId(1);
		for (Comment2 c : list) {
			System.out.println("id: " + c.getId());
			System.out.println("userName: " + c.getUserName());
			System.out.println("comment: " + c.getComment());
			System.out.println("createdAt: " + c.getCreatedAt());
			System.out.println("userId: " + c.getUserId());
			System.out.println("-------------------");
		}

		closeSqlSession(session);
	}

	/**
	 * ユーザデータの存在確認
	 * 
	 * @param userName
	 * @return
	 */
//	@ResultMap("commentResult1")
//	@Select("select * from user where user_name = #{userName}")
	static void selectUserByUserNameTest(String userName) {
		System.out.println("selectUserByUserNameTest");
		
		SqlSession session = openSqlSession();

		CommentDao2 dao = session.getMapper(CommentDao2.class);
		List<Comment2> list = dao.selectUserByUserName(userName);
		for (Comment2 c : list) {
			System.out.println("id: " + c.getId());
			System.out.println("userName: " + c.getUserName());
			System.out.println("comment: " + c.getComment());
			System.out.println("createdAt: " + c.getCreatedAt());
			System.out.println("userId: " + c.getUserId());
			System.out.println("-------------------");
		}
		closeSqlSession(session);
	}

	/**
	 * 最新のコメントIDを取得（かつ今回登録したコメント内容と合致するコメントのIDであること）
	 * 
	 * @return
	 */
//	@ResultMap("commentResult3")
//	@Select("select * from comments where comment = #{comment} and user_id = #{userId} order by created_at desc limit 1")
	static void selectLatestCommentTest() {
		System.out.println("selectLatestCommentTest");
		
		SqlSession session = openSqlSession();

		CommentDao2 dao = session.getMapper(CommentDao2.class);
		Comment2 c = dao.selectLatestComment("bbb", 19);
		System.out.println("id: " + c.getId());
		System.out.println("comment: " + c.getComment());
		System.out.println("createdAt: " + c.getCreatedAt());
		System.out.println("userId: " + c.getUserId());
		System.out.println("-------------------");
		closeSqlSession(session);
	}

	/**
	 * ユーザ名をユーザテーブルに登録
	 * 
	 * @param userName
	 */
//	@Insert("insert into user (user_name) values (#{userName});")
	static void insertUserNameTest() {
		System.out.println("insertUserNameTest");
		
		SqlSession session = openSqlSession();
		
		CommentDao2 dao = session.getMapper(CommentDao2.class);
		dao.insertUserName("eee");
		System.out.println("-------------------");
		commitAndCloseSqlSession(session);
	}

	/**
	 * コメントをコメントテーブルに登録
	 * 
	 * @param comment
	 * @param userId
	 */
//	@Insert("insert into comments (comment, user_id) values (#{text}, #{userId})")
	static void insertCommentTest(String comment, int userId) {
		System.out.println("insertCommentTest");
		
	}

	/**
	 * 返信コメントIDを返信テーブルに登録
	 * 
	 * @param commentId
	 * @param replyCommentId
	 */
//	@Insert("insert into replies (comment_id, reply_comment_id) values (#{id}, #{replyId})")
	static void insertReplyCommentIdTest(int commentId, int replyCommentId) {
		System.out.println("insertReplyCommentIdTest");
		
	}
}
