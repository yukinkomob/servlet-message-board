package com.sample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MeetupServlet
 */
@WebServlet("/meetup")
public class MeetupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MeetupServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();
		Object argIsReply = request.getParameter("isReply");
		boolean isReply = argIsReply != null ? argIsReply.equals("true") : false;

		if (isReply) {
			// 返信の処理
			int id = Integer.parseInt((String) request.getParameter("listId"));
			List<MeetupMessage> list = (List<MeetupMessage>) session.getAttribute("list");
			MeetupMessage msg = list.get(id);

			String name = request.getParameter("name");
			String comment = request.getParameter("comment");

// 			MeetupMessage replyMsg = new MeetupMessage(name, new Date(), comment);
//			msg.addReply(replyMsg);

			// TODO DBに保存
//			session.setAttribute("list", list);
			
			// TODO name, comment を Comment コンストラクタに渡してインスタンスを生成し、CommentDao#insert()に渡す
//			Comment comment = new Comment()

			RequestDispatcher rd = request.getRequestDispatcher("/meetup.jsp");
			rd.forward(request, response);
		} else {
			// コメントの処理

			List<MeetupMessage> list = (List<MeetupMessage>) session.getAttribute("list");

			if (list == null) {
				list = new ArrayList<>();
			}

			Integer id = (Integer) session.getAttribute("id");
			if (id == null) {
				id = 0;
			}

			String name = request.getParameter("name");
			String commentText = request.getParameter("comment");
			
			Comment comment = new Comment();
			comment.setUserName(name);
			comment.setText(commentText);
			CommentDao.insert(comment);

			RequestDispatcher rd = request.getRequestDispatcher("/meetup.jsp");
			rd.forward(request, response);
		}

		return;
	}

}
