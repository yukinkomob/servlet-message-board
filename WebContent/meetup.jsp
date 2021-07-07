<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Date"%>
<%@ page import="com.sample.Comment"%>
<%@ page import="com.sample.CommentDao"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nishimura - 自己紹介 -</title>
<link href="https://unpkg.com/tailwindcss@^2/dist/tailwind.min.css"
	rel="stylesheet">
</head>
<body>
	<%
		request.setCharacterEncoding("UTF-8");
	%>
	<header>
		<div
			class="h-10 leading-10 text-blue-100 bg-blue-900 text-center font-mono">
			<p class="">Information for JPIN's members</p>
		</div>
	</header>
	<main>
		<div class="cover relative">
			<img src="sky2.png" class="bg-green-100 w-full h-40">
			<div class="absolute top-1/2 -my-6 flex justify-center w-full">
				<p class="text-5xl text-white font-mono font-bold italic">自己紹介 -
					Nishimura -</p>
			</div>
		</div>
		<nav class="hidden flex justify-around text-center py-3">
			<div
				class="font-serif font-bold w-1/5 h-10 leading-10 text-blue-600 bg-yellow-300">
				<a href="">生まれた土地</a>
			</div>
			<div
				class="font-serif font-bold w-1/5 h-10 leading-10 text-blue-600 bg-yellow-300">
				<a href="">住んだ土地</a>
			</div>
			<div
				class="font-serif font-bold w-1/5 h-10 leading-10 text-blue-600 bg-yellow-300">
				<a href="">訪れた土地</a>
			</div>
		</nav>
		<div class="main flex">
			<div class="bg-gray-100 w-2/3">
				<div>
					<div class="bg-white m-8 text-center">
						<h2 class="text-3xl font-serif p-4 font-bold bg-gray-100">趣味</h2>
					</div>
					<div
						class="bg-blue-200 m-4 rounded-3xl shadow-xl rounded-t-full hover:rounded-none duration-300">
						<div>
							<h3 class="text-2xl font-serif pt-8 pl-8">スポーツ</h3>
							<img src="">
							<p class="p-8 font-serif">学生時代はバスケ部に入りましたが、集団スポーツに全く適応せずに撃沈・・・！それ以降は、拳法や水泳など個人競技に打ち込みました。社会人になってからは、ランニングやボルダリングなどを楽しんでいます。冬には友人とスキーなどにも行きます。</p>
						</div>
					</div>
					<div>
						<div
							class="bg-yellow-100 m-4 shadow-xl rounded-full hover:rounded-none duration-300">
							<h3 class="text-2xl font-serif pt-8 pl-8">読書</h3>
							<img src="">
							<p class="p-8 font-serif">基本的にはインドアの生活の自分にとって読書は有益な時間を提供してくれます。読めない量の書籍を買い続けた失敗から、それらを裁断して電子書籍化して以降、Kindleが重要なパートナーとなっています。最近はセールにつられてドラゴン桜や数学ガールを大量購入し、それらも含めて日々読書を楽しんでいます。</p>
						</div>
					</div>
					<div>
						<div
							class="bg-purple-200 m-4 rounded-b-full shadow-xl hover:rounded-none duration-300">
							<h3 class="text-2xl font-serif pt-8 pl-8">暗記</h3>
							<img src="">
							<p class="p-8 font-serif">学生時代、いや社会人になってからも暗記より理解することを重視してきたものの、最近は理解した内容すら忘れていくことから、考えを改め記憶力を増強するために暗記を習慣付けています。2021/1から全国の市町村の暗記を続けて、2021/5にようやく完了。最後の北海道の179市町村の暗記には苦労しました。</p>
						</div>
					</div>
				</div>
				<div>
					<div class="bg-white m-8 text-center">
						<h2 class="text-3xl font-serif p-4 font-bold bg-gray-100">JPIN前期の受講内容で好きなところ</h2>
					</div>
					<div class="flex justify-around flex-wrap mb-20">
						<div
							class="w-2/5 bg-red-200 shadow-lg mb-8 rounded-br-full hover:rounded-none duration-300">
							<h3 class="text-2xl font-serif p-2">JVMの動作仕様</h3>
							<img src="">
							<p class="p-2 font-serif">最初にJVMの精緻な説明をされたのは衝撃でした。理解するのも大変でした。しかし、あらかじめ背後で何が行われているかを理解することで、それ以降の学習はかなり理解しやすかったと思います。今後別の言語を学ぶ時でも背後に意識を向ける習慣が得られたことは大きな武器になると思っています。</p>
						</div>
						<div
							class="w-2/5 bg-yellow-100 shadow-lg mb-8 rounded-bl-full hover:rounded-none duration-300">
							<h3 class="text-2xl font-serif p-2">モデリング</h3>
							<img src="">
							<p class="p-2 font-serif">単にコードの設計図というだけでなく、コードを伴わない抽象的なレベルでモデリングに取り組み、先生からレビューしてもらえたという機会はとても貴重であったと思っています。課題は難しく、合格を頂くことが出来ませんでしたが、設計でUMLを書く時などに抽象的なモデルを意識していきたいと思っています。</p>
						</div>
						<div
							class="w-2/5 bg-blue-200 shadow-lg rounded-tr-full hover:rounded-none duration-300">
							<h3 class="text-2xl font-serif p-2">デザインパターン</h3>
							<img src="">
							<p class="p-2 font-serif">実際にコードを書くことを通して学んだおかげでスッキリと理解できました。また、現場でどのように活用されるかというところも併せて伺うことが出来たので、設計の時に役立つと思います。実際には23個全部使うような人はほとんどおらず、7,
								8個のパターンを活用して設計していく人が多いということですので、自分が得意とするパターンを選んでいけたらと思っています。</p>
						</div>
						<div
							class="w-2/5 bg-green-100 shadow-lg rounded-tl-full hover:rounded-none duration-300">
							<h3 class="text-2xl font-serif p-2">演習課題</h3>
							<img src="">
							<p class="p-2 font-serif">分量はかなり多かったのですが、最初の課題をこなした後、小刻みにレベルアップした課題が次々出てくるという感じでストレスを余り感じずに進めていくことが出来ました。毎回クラス図や要件定義がしっかりされているのも非常に良く、そこから学べるものも多いと思いました。エンジニア向けのケーススタディといった趣きで、楽しく取り組むことができました。</p>
						</div>
					</div>
				</div>
			</div>
			<aside class="w-1/3 bg-gradient-to-b from-blue-400 to-white">
				<div class="m-8 text-center">
					<h2
						class="text-xl font-sans tracking-widest p-4 font-bold text-white">フォローアカウント</h2>
				</div>
				<div class="m-8 text-center">
					<a class="twitter-timeline" data-width="360" data-height="480"
						href="https://twitter.com/betteroneself?ref_src=twsrc%5Etfw">Tweets
						by betteroneself</a>
					<script async src="https://platform.twitter.com/widgets.js"
						charset="utf-8"></script>
				</div>
				<div class="m-8 text-center">
					<a class="twitter-timeline" data-width="360" data-height="480"
						href="https://twitter.com/hyuki?ref_src=twsrc%5Etfw">Tweets by
						hyuki</a>
					<script async src="https://platform.twitter.com/widgets.js"
						charset="utf-8"></script>
				</div>
				<div class="m-8 text-center">
					<a class="twitter-timeline" data-width="360" data-height="480"
						href="https://twitter.com/mita_norifusa?ref_src=twsrc%5Etfw">Tweets
						by mita_norifusa</a>
					<script async src="https://platform.twitter.com/widgets.js"
						charset="utf-8"></script>
				</div>
			</aside>
		</div>
		<div id="message_board"
			class="p-8 from-green-300 to-green-100 bg-gradient-to-b">
			<h2
				class="text-center text-white text-3xl mb-8 tracking-widest font-bold">掲示板</h2>
			<div class="bg-white rounded-xl w-full p-4">
				<!-- コメントのフォーム -->
				<form action="meetup" method="post">
					<input class="border-2  p-1 pl-4" type="text" name="name"
						placeholder="名　前">
					<hr class="my-4 border-green-200">
					<textarea class="border-2 w-full h-24 p-2" name="comment"
						placeholder="コメントを書いてください"></textarea>
					<input
						class="bg-green-500 hover:bg-green-300 active:bg-yellow-300 text-white w-28 text-xl rounded-lg p-1 my-4 shadow-md cursor-pointer"
						type="submit" value="送　信">
				</form>
			</div>
			<hr class="my-4">
			<!-- メッセージを表示する -->
			<div class="flex flex-col">
				<!-- コメントデータを生成 -->

				<!-- リストを取得する -->
				<%
					List<Comment> comments = CommentDao.selectComment();

				request.setAttribute("data", comments);
				%>

				<c:forEach items="${ data }" var="msg" varStatus="status">

					<div class="flex justify-between p-4">
						<div class="bg-white w-44 h-64 rounded-xl p-4">
							<div class="w-36 h-40 bg-yellow-100"></div>
							<div>
								<p class="text-center font-bold p-2">
									<c:out value="${ msg.userName }" />
								</p>
								<c:set var="temp_date" value="${ msg.createdAt }" />
								<%
									Date date = (Date) pageContext.getAttribute("temp_date");
								SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd E HH:mm:ss");
								pageContext.setAttribute("date", df.format(date));
								%>
								<p class="text-center text-sm text-gray-400">
									<c:out value="${ date }" />
								</p>
							</div>
						</div>
						<div
							class="w-10 h-10 bg-white mt-8 ml-8 transform rotate-45 translate-x-5 z-0">
						</div>
						<!-- 返信コメント -->
						<div class="bg-white w-full h-auto mr-12 rounded-2xl p-6">
							<p>
								<c:out value="${ msg.text }" />
							</p>
							<hr class="my-4 border-green-200">
							<!-- 返信のフォーム -->
							<form action="meetup" method="post">
								<input type="hidden" name="isReply" value="true"> <input
									type="hidden" name="listId" value="${ msg.id - 1 }"> <input
									class="border-2 p-1 pl-4 z-10 relative mb-2" type="text"
									name="name" placeholder="名　前"> <input
									class="border-2 w-full p-2" type="text" name="comment"
									placeholder="返信テキスト"> <input
									class="text-blue-400 hover:text-blue-600 bg-white text-lg cursor-pointer my-2"
									type="submit" value="返信する">
							</form>
							<!-- 返信メッセージ -->
							<c:forEach items="${ msg.replyList }" var="rmsg"
								varStatus="status">

								<div class="flex justify-end flex-wrap">
									<div class="bg-pink-50 w-2/3 h-32 m-2 rounded-xl p-4 text-left">
										<p class="text-xs">
											<c:out value="${ rmsg.userName }" />
										</p>
										<c:set var="temp_rep_date" value="${ rmsg.createdAt }" />
										<%
											Date date2 = (Date) pageContext.getAttribute("temp_rep_date");
										SimpleDateFormat df2 = new SimpleDateFormat("yyyy/MM/dd E HH:mm:ss");
										pageContext.setAttribute("reply_date", df2.format(date2));
										%>
										<p class="text-xs">
											<c:out value="${ reply_date }" />
										</p>
										<hr class="my-2">
										<p class="text-sm">
											<c:out value="${ rmsg.text }" />
										</p>
									</div>
								</div>

							</c:forEach>

						</div>
					</div>

				</c:forEach>

			</div>
		</div>
	</main>
	<footer
		class="h-10 leading-10 text-blue-100 bg-blue-900 text-center font-mono">
		<p class="">Created at 2021-05-27 15:14:07</p>
	</footer>
</body>
</html>
