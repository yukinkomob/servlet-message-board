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

import com.sample.dao.CommentDao;

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
			// ï‘êMÇÃèàóù
			int id = Integer.parseInt((String) request.getParameter("msgId"));

			String name = request.getParameter("name");
			String commentText = request.getParameter("comment");
			
			Comment comment = new Comment();
			comment.setUserName(name);
			comment.setText(commentText);
			
			CommentDao.insertReply(id, comment);

			RequestDispatcher rd = request.getRequestDispatcher("/meetup.jsp");
			rd.forward(request, response);
		} else {
			// ÉRÉÅÉìÉgÇÃèàóù

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
