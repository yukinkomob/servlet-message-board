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
	 * �S�ẴR�����g�f�[�^����������
	 * 
	 * @param includeReply �ԐM���܂ނ��ǂ���
	 * @return
	 * @throws SQLException
	 */
	public static List<Comment2> selectComment() {
		SqlSession session = openSqlSession();
		
		CommentDao2 dao = session.getMapper(CommentDao2.class);
		List<Comment2> commentList = dao.selectAllComments();

		for (Comment2 c : commentList) {
			// �ԐM�R�����g��ԐM���X�g�ɐݒ�
			List<Comment2> replyComments = new ArrayList<>();
			
			// �w��R�����gID�ɕR�Â��ԐMID���擾
			List<Comment2> replyIds = dao.selectReplyIdsByCommentId(c.getId());

			for (Comment2 replyId : replyIds) {
				// �ԐMID���w�肵�ăR�����g�f�[�^���擾
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

		// ���[�U�f�[�^�̑��݊m�F
		List<Comment2> users = dao.selectUsersByUserName(comment.getUserName());
		int userId = -1;
		if (users.size() == 0) {
			// ���[�U�������[�U�e�[�u���ɓo�^
			dao.insertUserName(comment.getUserName());
			commitSqlSession(session);

			// ���[�UID�̎擾
			users = dao.selectUsersByUserName(comment.getUserName());
			if (users.size() != 0) {
				userId = users.get(0).getId();
			}
		}

		// �R�����g���R�����g�e�[�u���ɓo�^
		dao.insertComment(comment.getText(), userId);
		commitAndCloseSqlSession(session);
	}

	/**
	 * 
	 * @param commentId �ԐM���̃R�����gID
	 * @param replyComment �ԐM�R�����g
	 */
	public static void insertReply(int commentId, Comment2 replyComment) {
		SqlSession session = openSqlSession();
		CommentDao2 dao = session.getMapper(CommentDao2.class);

		// ���[�U�f�[�^�̑��݊m�F
		List<Comment2> users = dao.selectUsersByUserName(replyComment.getUserName());
			
		int userId = -1;
		if (users.size() == 0) {
			// ���[�U�������[�U�e�[�u���ɓo�^
			dao.insertUserName(replyComment.getUserName());
			commitSqlSession(session);
			
			// ���[�UID�̎擾
			users = dao.selectUsersByUserName(replyComment.getUserName());
			if (users.size() != 0) {
				userId = users.get(0).getId();
			}
		}
		
		// �R�����g���R�����g�e�[�u���ɓo�^
		dao.insertComment(replyComment.getComment(), userId);
		commitSqlSession(session);
		
		// �ŐV�̃R�����gID���擾�i������o�^�����R�����g���e�ƍ��v����R�����g��ID�ł��邱�Ɓj
		Comment2 c = dao.selectLatestComment(replyComment.getComment(), userId);
		
		int insertedCommentId = -1;
		if (c != null) {
			insertedCommentId = c.getId();
		}
		if (insertedCommentId == -1) {
			throw new IllegalStateException("bad implementation.");
		}
		
		// �ԐM�R�����gID��ԐM�e�[�u���ɓo�^
		dao.insertReplyCommentId(commentId, insertedCommentId);
		
		commitAndCloseSqlSession(session);
	}
}
