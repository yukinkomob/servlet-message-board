package com.sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentDao {

	private Connection openConnection() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/message_board?useSSL=false";
		String user = "root";
		String password = "pappasu24";

		return DriverManager.getConnection(url, user, password);
	}

	/**
	 * �S�ẴR�����g�f�[�^����������
	 * 
	 * @param includeReply �ԐM���܂ނ��ǂ���
	 * @return
	 * @throws SQLException
	 */
	public List<Comment> selectComment() {
		Connection con = null;
		List<Comment> list = new ArrayList<>();
		try {
			con = openConnection();
			PreparedStatement ps = con.prepareStatement(
					"select comments.id, comments.comment, comments.created_at, comments.user_id, user.user_name from comments inner join user on user.id = comments.user_id where not exists (select * from replies where comments.id = replies.reply_comment_id)");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Comment comment = new Comment();
				comment.setId(rs.getInt("id"));
				comment.setUserName(rs.getString("user_name"));
				Timestamp ts = rs.getTimestamp("created_at");
				Date date = new Date(ts.getTime());
				comment.setCreatedAt(date);
				comment.setText(rs.getString("comment"));

				System.out.println("id: " + comment.getId() + ", user_name: " + comment.getUserName() + ", date: "
						+ comment.getCreatedAt().toString() + ", comment: " + comment.getText());
				list.add(comment);

				// �ԐM�R�����g��ԐM���X�g�ɐݒ�
				List<Comment> replyComments = new ArrayList<>();
				{
					// �w��R�����gID�ɕR�Â��ԐMID���擾
					PreparedStatement ps2 = con.prepareStatement("select * from replies where comment_id = ?");
					ps2.setInt(1, comment.getId());
					ResultSet rs2 = ps2.executeQuery();

					while (rs2.next()) {
						int replyId = rs2.getInt("reply_comment_id");

						// �ԐMID���w�肵�ăR�����g�f�[�^���擾
						PreparedStatement ps3 = con.prepareStatement(
								"select comments.id, comment, created_at, user_id, user_name from comments inner join user where comments.id = ? and comments.user_id = user.id");
						ps3.setInt(1, replyId);
						ResultSet rs3 = ps3.executeQuery();

						while (rs3.next()) {
							// �ԐM�R�����g�̃C���X�^���X�𐶐�
							Comment replyComment = new Comment();
							replyComment.setId(rs3.getInt("id"));
							replyComment.setUserName(rs3.getString("user_name"));
							Timestamp ts2 = rs3.getTimestamp("created_at");
							Date date2 = new Date(ts2.getTime());
							replyComment.setCreatedAt(date2);
							replyComment.setText(rs3.getString("comment"));
							
							System.out.println("    id: " + replyComment.getId() + ", user_name: " + replyComment.getUserName() + ", date: "
									+ replyComment.getCreatedAt().toString() + ", replyComment: " + replyComment.getText());

							replyComments.add(replyComment);
						}
					}
				}
				comment.setReplyList(replyComments);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	public void insert(Comment comment) {

	}
}
