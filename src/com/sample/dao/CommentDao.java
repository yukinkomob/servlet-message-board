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

	// �폜����\��
	private static Connection openConnection() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/message_board?useSSL=false";
		String user = "root";
		String password = "pappasu24";

		return DriverManager.getConnection(url, user, password);
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
//		System.out.println("selectComment");
		{
			// �e�X�g�p���\�b�h�̎��s
//			CommentDao2Test.execute();
		}
		
//		Connection con = null;
//		List<Comment> list = new ArrayList<>();
//		try {
//			con = openConnection();
//			PreparedStatement ps = con.prepareStatement(
//					"select comments.id, comments.comment, comments.created_at, comments.user_id, user.user_name from comments inner join user on user.id = comments.user_id where not exists (select * from replies where comments.id = replies.reply_comment_id)");
//			ResultSet rs = ps.executeQuery();
			
			SqlSession session = openSqlSession();
			
			CommentDao2 dao = session.getMapper(CommentDao2.class);
			List<Comment2> commentList = dao.selectAllComments();

			for (Comment2 c : commentList) {
//				System.out.println("selectComment2");
//			while (rs.next()) {
//				Comment comment = new Comment();
//				comment.setId(rs.getInt("id"));
//				comment.setUserName(rs.getString("user_name"));
//				Timestamp ts = rs.getTimestamp("created_at");
//				Date date = new Date(ts.getTime());
//				comment.setCreatedAt(date);
//				comment.setText(rs.getString("comment"));

				
//				System.out.println("id: " + comment.getId() + ", user_name: " + comment.getUserName() + ", date: "
//						+ comment.getCreatedAt().toString() + ", comment: " + comment.getText());
//				list.add(comment);

				// �ԐM�R�����g��ԐM���X�g�ɐݒ�
//				List<Comment> replyComments = new ArrayList<>();
				List<Comment2> replyComments = new ArrayList<>();
				
					// �w��R�����gID�ɕR�Â��ԐMID���擾
//					PreparedStatement ps2 = con.prepareStatement("select * from replies where comment_id = ?");
//					ps2.setInt(1, comment.getId());
//					ResultSet rs2 = ps2.executeQuery();
					
				List<Comment2> replyIds = dao.selectReplyIdsByCommentId(c.getId());

				for (Comment2 replyId : replyIds) {
//					System.out.println("selectComment3");
//					while (rs2.next()) {
//						int replyId = rs2.getInt("reply_comment_id");

						// �ԐMID���w�肵�ăR�����g�f�[�^���擾
//						PreparedStatement ps3 = con.prepareStatement(
//								"select comments.id, comment, created_at, user_id, user_name from comments inner join user where comments.id = ? and comments.user_id = user.id");
//						ps3.setInt(1, replyId);
//						ResultSet rs3 = ps3.executeQuery();
						
					replyComments = dao.selectCommentByCommentId(replyId.getReplyId());
//					for (Comment2 rc : replyComments) {
//						for (Comment2 replyComment : replyComments) {
//						while (rs3.next()) {
							// �ԐM�R�����g�̃C���X�^���X�𐶐�
//							Comment replyComment = new Comment();
//							replyComment.setId(rs3.getInt("id"));
//							replyComment.setUserName(rs3.getString("user_name"));
//							Timestamp ts2 = rs3.getTimestamp("created_at");
//							Date date2 = new Date(ts2.getTime());
//							replyComment.setCreatedAt(date2);
//							replyComment.setText(rs3.getString("comment"));

//							System.out.println("    id: " + replyComment.getId() + ", user_name: "
//									+ replyComment.getUserName() + ", date: " + replyComment.getCreatedAt().toString()
//									+ ", replyComment: " + replyComment.getText());

//							replyComments.add(replyComment);
//						replyComments.add(rc);
//					}
				}
				c.setReplyList(replyComments);
			}
			closeSqlSession(session);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		System.out.println("selectComment999");
		return commentList;
	}

	public static void insert(Comment comment) {
//		Connection con = null;
		
		SqlSession session = openSqlSession();
		CommentDao2 dao = session.getMapper(CommentDao2.class);

//		try {
//			con = openConnection();
			
		// ���[�U�f�[�^�̑��݊m�F
		List<Comment2> users = dao.selectUsersByUserName(comment.getUserName());
		
//			String userName = comment.getUserName();
//			PreparedStatement ps = con.prepareStatement("select * from user where user_name = ?");
//			ps.setString(1, userName);
//			ResultSet rs = ps.executeQuery();
			
//			int userId = -1;
//			boolean existUserName = false; 
//			while (rs.next()) {
//				userId = rs.getInt("id");
//				existUserName = true;
//			}
			
//			if (!existUserName) {
		int userId = -1;
		if (users.size() == 0) {
			// ���[�U�������[�U�e�[�u���ɓo�^
			dao.insertUserName(comment.getUserName());
			commitSqlSession(session);
			
//			ps = con.prepareStatement("insert into user (user_name) values (?);");
//			ps.setString(1, comment.getUserName());
//			ps.execute();
			// ���[�UID�̎擾
			users = dao.selectUsersByUserName(comment.getUserName());
//			ps = con.prepareStatement("select * from user where user_name = ?");
//			ps.setString(1, comment.getUserName());
//			rs = ps.executeQuery();
			
			if (users.size() != 0) {
				userId = users.get(0).getId();
			}
//			while (rs.next()) {
//				userId = rs.getInt("id");
//			}
		}
			
			// �R�����g���R�����g�e�[�u���ɓo�^
			dao.insertComment(comment.getText(), userId);
			commitSqlSession(session);
		
//			ps = con.prepareStatement("insert into comments (comment, user_id) values (?, ?)");
//			ps.setString(1, comment.getText());
//			ps.setInt(2, userId);
//			ps.execute();

//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
		closeSqlSession(session);
	}

	/**
	 * 
	 * @param commentId �ԐM���̃R�����gID
	 * @param replyComment �ԐM�R�����g
	 */
	public static void insertReply(int commentId, Comment2 replyComment) {
		SqlSession session = openSqlSession();
		CommentDao2 dao = session.getMapper(CommentDao2.class);
//		Connection con = null;

//		try {
//			con = openConnection();
			
			// ���[�U�f�[�^�̑��݊m�F
			List<Comment2> users = dao.selectUsersByUserName(replyComment.getUserName());
			
//			String userName = replyComment.getUserName();
//			PreparedStatement ps = con.prepareStatement("select * from user where user_name = ?");
//			ps.setString(1, userName);
//			ResultSet rs = ps.executeQuery();
			
//			int userId = -1;
//			boolean existUserName = false; 
//			while (rs.next()) {
//				userId = rs.getInt("id");
//				existUserName = true;
//			}
			
			int userId = -1;
			if (users.size() == 0) {
				// ���[�U�������[�U�e�[�u���ɓo�^
				dao.insertUserName(replyComment.getUserName());
				commitSqlSession(session);
			
//			if (!existUserName) {
				// ���[�U�������[�U�e�[�u���ɓo�^
//				ps = con.prepareStatement("insert into user (user_name) values (?);");
//				ps.setString(1, replyComment.getUserName());
//				ps.execute();
				
				// ���[�UID�̎擾
				users = dao.selectUsersByUserName(replyComment.getUserName());
				if (users.size() != 0) {
					userId = users.get(0).getId();
				}
//				ps = con.prepareStatement("select * from user where user_name = ?");
//				ps.setString(1, replyComment.getUserName());
//				rs = ps.executeQuery();
//				while (rs.next()) {
//					userId = rs.getInt("id");
//				}
			}
			
			// �R�����g���R�����g�e�[�u���ɓo�^
			dao.insertComment(replyComment.getComment(), userId);
			commitSqlSession(session);
			
//			ps = con.prepareStatement("insert into comments (comment, user_id) values (?, ?)");
//			ps.setString(1, replyComment.getText());
//			ps.setInt(2, userId);
//			ps.execute();
			
			// �ŐV�̃R�����gID���擾�i������o�^�����R�����g���e�ƍ��v����R�����g��ID�ł��邱�Ɓj
			Comment2 c = dao.selectLatestComment(replyComment.getComment(), userId);
			
//			ps = con.prepareStatement("select * from comments where comment = ? and user_id = ? order by created_at desc limit 1");
//			ps.setString(1, replyComment.getComment());
//			ps.setInt(2,  userId);
//			rs = ps.executeQuery();
			int insertedCommentId = -1;
//			while (rs.next()) {
//				insertedCommentId = rs.getInt("id");
//			}
			if (c != null) {
				insertedCommentId = c.getId();
			}
			if (insertedCommentId == -1) {
				throw new IllegalStateException("bad implementation.");
			}
			
			// �ԐM�R�����gID��ԐM�e�[�u���ɓo�^
			dao.insertReplyCommentId(commentId, insertedCommentId);
			commitSqlSession(session);
//			ps = con.prepareStatement("insert into replies (comment_id, reply_comment_id) values (?, ?)");
//			ps.setInt(1, commentId);
//			ps.setInt(2,  insertedCommentId);
//			ps.execute();

//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
		closeSqlSession(session);
	}
}
