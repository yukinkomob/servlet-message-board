package com.sample.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.sample.Comment;
import com.sample.Comment2;

public class CommentDao {
	
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

	static void commitSqlSession(SqlSession session) {
		session.commit();
	}
	
	static void commitAndCloseSqlSession(SqlSession session) {
		session.commit();
		session.close();
	}

	static void closeSqlSession(SqlSession session) {
		session.close();
	}

	/**
	 * 全てのコメントデータを検索する
	 * 
	 * @param includeReply 返信を含むかどうか
	 * @return
	 * @throws SQLException
	 */
	public static List<Comment2> selectComment() {
		SqlSession session = openSqlSession();
		
		CommentDao2 dao = session.getMapper(CommentDao2.class);
		List<Comment2> commentList = dao.selectAllComments();

		for (Comment2 c : commentList) {
			// 返信コメントを返信リストに設定
			List<Comment2> replyComments = new ArrayList<>();
			
			// 指定コメントIDに紐づく返信IDを取得
			List<Comment2> replyIds = dao.selectReplyIdsByCommentId(c.getId());

			for (Comment2 replyId : replyIds) {
				// 返信IDを指定してコメントデータを取得
				replyComments = dao.selectCommentByCommentId(replyId.getReplyId());
			}
			c.setReplyList(replyComments);
		}
		closeSqlSession(session);

		return commentList;
	}

	public static void insert(Comment comment) {
		SqlSession session = openSqlSession();
		CommentDao2 dao = session.getMapper(CommentDao2.class);

		// ユーザデータの存在確認
		List<Comment2> users = dao.selectUsersByUserName(comment.getUserName());
		int userId = -1;
		if (users.size() == 0) {
			// ユーザ名をユーザテーブルに登録
			dao.insertUserName(comment.getUserName());
			commitSqlSession(session);

			// ユーザIDの取得
			users = dao.selectUsersByUserName(comment.getUserName());
			if (users.size() != 0) {
				userId = users.get(0).getId();
			}
		}

		// コメントをコメントテーブルに登録
		dao.insertComment(comment.getText(), userId);
		commitAndCloseSqlSession(session);
	}

	/**
	 * 
	 * @param commentId 返信元のコメントID
	 * @param replyComment 返信コメント
	 */
	public static void insertReply(int commentId, Comment2 replyComment) {
		SqlSession session = openSqlSession();
		CommentDao2 dao = session.getMapper(CommentDao2.class);

		// ユーザデータの存在確認
		List<Comment2> users = dao.selectUsersByUserName(replyComment.getUserName());
			
		int userId = -1;
		if (users.size() == 0) {
			// ユーザ名をユーザテーブルに登録
			dao.insertUserName(replyComment.getUserName());
			commitSqlSession(session);
			
			// ユーザIDの取得
			users = dao.selectUsersByUserName(replyComment.getUserName());
			if (users.size() != 0) {
				userId = users.get(0).getId();
			}
		}
		
		// コメントをコメントテーブルに登録
		dao.insertComment(replyComment.getComment(), userId);
		commitSqlSession(session);
		
		// 最新のコメントIDを取得（かつ今回登録したコメント内容と合致するコメントのIDであること）
		Comment2 c = dao.selectLatestComment(replyComment.getComment(), userId);
		
		int insertedCommentId = -1;
		if (c != null) {
			insertedCommentId = c.getId();
		}
		if (insertedCommentId == -1) {
			throw new IllegalStateException("bad implementation.");
		}
		
		// 返信コメントIDを返信テーブルに登録
		dao.insertReplyCommentId(commentId, insertedCommentId);
		
		commitAndCloseSqlSession(session);
	}
}
