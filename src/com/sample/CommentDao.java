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

	private static Connection openConnection() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/message_board?useSSL=false";
		String user = "root";
		String password = "pappasu24";

		return DriverManager.getConnection(url, user, password);
	}

	/**
	 * 全てのコメントデータを検索する
	 * 
	 * @param includeReply 返信を含むかどうか
	 * @return
	 * @throws SQLException
	 */
	public static List<Comment> selectComment() {
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

				// 返信コメントを返信リストに設定
				List<Comment> replyComments = new ArrayList<>();
				{
					// 指定コメントIDに紐づく返信IDを取得
					PreparedStatement ps2 = con.prepareStatement("select * from replies where comment_id = ?");
					ps2.setInt(1, comment.getId());
					ResultSet rs2 = ps2.executeQuery();

					while (rs2.next()) {
						int replyId = rs2.getInt("reply_comment_id");

						// 返信IDを指定してコメントデータを取得
						PreparedStatement ps3 = con.prepareStatement(
								"select comments.id, comment, created_at, user_id, user_name from comments inner join user where comments.id = ? and comments.user_id = user.id");
						ps3.setInt(1, replyId);
						ResultSet rs3 = ps3.executeQuery();

						while (rs3.next()) {
							// 返信コメントのインスタンスを生成
							Comment replyComment = new Comment();
							replyComment.setId(rs3.getInt("id"));
							replyComment.setUserName(rs3.getString("user_name"));
							Timestamp ts2 = rs3.getTimestamp("created_at");
							Date date2 = new Date(ts2.getTime());
							replyComment.setCreatedAt(date2);
							replyComment.setText(rs3.getString("comment"));

							System.out.println("    id: " + replyComment.getId() + ", user_name: "
									+ replyComment.getUserName() + ", date: " + replyComment.getCreatedAt().toString()
									+ ", replyComment: " + replyComment.getText());

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

	public static void insert(Comment comment) {
		Connection con = null;

		try {
			con = openConnection();
			
			// ユーザデータの存在確認
			String userName = comment.getUserName();
			PreparedStatement ps = con.prepareStatement("select * from user where user_name = ?");
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
			
			int userId = -1;
			boolean existUserName = false; 
			while (rs.next()) {
				userId = rs.getInt("id");
				existUserName = true;
			}
			
			if (!existUserName) {
				// ユーザ名をユーザテーブルに登録
				ps = con.prepareStatement("insert into user (user_name) values (?);");
				ps.setString(1, comment.getUserName());
				ps.execute();
				// ユーザIDの取得
				ps = con.prepareStatement("select * from user where user_name = ?");
				ps.setString(1, comment.getUserName());
				rs = ps.executeQuery();
				while (rs.next()) {
					userId = rs.getInt("id");
				}
			}
			
			// コメントをコメントテーブルに登録
			ps = con.prepareStatement("insert into comments (comment, user_id) values (?, ?)");
			ps.setString(1, comment.getText());
			ps.setInt(2, userId);
			ps.execute();

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
	}

	/**
	 * 
	 * @param commentId 返信元のコメントID
	 * @param replyComment 返信コメント
	 */
	public static void insertReply(int commentId, Comment replyComment) {
		Connection con = null;

		try {
			con = openConnection();
			
			// ユーザデータの存在確認
			String userName = replyComment.getUserName();
			PreparedStatement ps = con.prepareStatement("select * from user where user_name = ?");
			ps.setString(1, userName);
			ResultSet rs = ps.executeQuery();
			
			int userId = -1;
			boolean existUserName = false; 
			while (rs.next()) {
				userId = rs.getInt("id");
				existUserName = true;
			}
			
			if (!existUserName) {
				// ユーザ名をユーザテーブルに登録
				ps = con.prepareStatement("insert into user (user_name) values (?);");
				ps.setString(1, replyComment.getUserName());
				ps.execute();
				// ユーザIDの取得
				ps = con.prepareStatement("select * from user where user_name = ?");
				ps.setString(1, replyComment.getUserName());
				rs = ps.executeQuery();
				while (rs.next()) {
					userId = rs.getInt("id");
				}
			}
			
			// コメントをコメントテーブルに登録
			ps = con.prepareStatement("insert into comments (comment, user_id) values (?, ?)");
			ps.setString(1, replyComment.getText());
			ps.setInt(2, userId);
			ps.execute();
			
			// 最新のコメントIDを取得（かつ今回登録したコメント内容と合致するコメントのIDであること）
			ps = con.prepareStatement("select * from comments where comment = ? and user_id = ? order by created_at desc limit 1");
			ps.setString(1, replyComment.getText());
			ps.setInt(2,  userId);
			rs = ps.executeQuery();
			int insertedCommentId = -1;
			while (rs.next()) {
				insertedCommentId = rs.getInt("id");
			}
			if (insertedCommentId == -1) {
				throw new IllegalStateException("bad implementation.");
			}
			
			// 返信コメントIDを返信テーブルに登録
			ps = con.prepareStatement("insert into replies (comment_id, reply_comment_id) values (?, ?)");
			ps.setInt(1, commentId);
			ps.setInt(2,  insertedCommentId);
			ps.execute();

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
	}
}
