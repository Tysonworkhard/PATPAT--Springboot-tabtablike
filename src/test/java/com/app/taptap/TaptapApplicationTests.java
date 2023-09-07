package com.app.taptap;

import com.app.taptap.mapper.*;
import com.app.taptap.pojo.*;
import com.app.taptap.service.AdaptiveRankScore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


@SpringBootTest
class TaptapApplicationTests {
	@Autowired
	RegisterMapper registerMapper;
	@Autowired
	LoginMapper loginMapper;
	@Autowired
	PersonInfoMapper personInfoMapper;

	@Autowired
	MainPageMapper mainPageMapper;

	@Autowired
	CommunityMapper communityMapper;
	@Autowired
	SearchMapper searchMapper;
	@Autowired
	RankMapper rankMapper;
	@Autowired
	FindGamesMapper findGamesMapper;
	@Autowired
	ChatMapper chatMapper;

	@Test
	//测试注册数据库接口
	void testRegister(){
		//registerMapper.insertUser(new RegistrationRequest("1111","1111","1111","11111"));
		User user = loginMapper.getPwdByPhone("18029562278");
		if(user == null){
			System.out.println("空");
		}else {
			System.out.println(user.getPassword());
		}

	}
	@Test
	void testLogin(){
		User user = loginMapper.getUserByPhone("18029562278");
		System.out.println(user.getPassword());
	}

	@Test
	void testPersonInfo(){
		/*List<Game> gameid = personInfoMapper.getUserAllGame("111");
		List<Game> games= personInfoMapper.getGameInfo(gameid);
		System.out.println(games);*/
		/*List<Tag> tagid = personInfoMapper.getUserAllLikeTag("111");
		List<Tag> tags= personInfoMapper.getTagInfo(tagid);
		System.out.println(tags);*/
		/*PersonInfoChangeRequest personInfoChangeRequest=new PersonInfoChangeRequest();
		personInfoChangeRequest.setUsername("bbbbb");
		personInfoChangeRequest.setPhoneNumber("444444444");
		personInfoChangeRequest.setEmail("rffererfe");
		personInfoChangeRequest.setProfile("5c454f4ew");
		personInfoMapper.updateUserInfoByUID("111",personInfoChangeRequest);*/
		/*User user = personInfoMapper.getUserUIDByPhoneNum("18029562278");
		System.out.println(user.getUID());*/
		personInfoMapper.updateUserProfile("111","qwert");
	}

	@Test
	void testMainPage(){
		List<Game> game = mainPageMapper.getAllGamesDateAndGames();
		System.out.println(game.get(0).getGameDate());
	}

	@Test
	void testCommunity(){
		/*List<Tag> tag=communityMapper.selectTagsWriteByUser("33333");
		System.out.println(tag);
		List<Game> game = communityMapper.getAllGames_simple();
		System.out.println(game);*/
		/*List<TagsResponse>tagProposed=communityMapper.selectTagsWithUserCount();
		System.out.println(tagProposed.get(0).getUserCount());*/
		/*User user=communityMapper.getUserInfo_simple("111");
		System.out.println(user.getUsername());*/
		/*List<Comment> comment=communityMapper.getCommentByTagID("565");
		System.out.println(comment.get(0).getPictureAddress().size());
		System.out.println(comment.get(1).getPictureAddress().size());*/
//		Tag tag=new Tag();
//		tag.setTagID("8888");
//		tag.setTime("2023-07-23 11:53:22");
//		tag.setTitle("jjj");
//		tag.setText("submitTagRequest.getTagText()");
//		tag.setGameID("10");
//		communityMapper.insertTagsByUID(tag,"111");
//		User user=communityMapper.isGood("9999","33333");
//		System.out.println(user.getUID());
//		communityMapper.goodCountDown1("222");


	}

	@Test
	void testSearch(){
//		List<Game> game = searchMapper.searchGamesByGameName("米老鼠");
//		System.out.println(game.get(0).getScore());
		searchMapper.updateGameSearchCount("米老鼠");
	}

	@Test
	void testRank(){
		List<RankAllGame> rankAllGames=new ArrayList<>();
		RankAllGame rankAllGame1=new RankAllGame();
		rankAllGame1.setRankType("hot");
		rankAllGame1.setGameID("1");
		rankAllGame1.setRankScore(100.0F);
		RankAllGame rankAllGame2=new RankAllGame();
		rankAllGame2.setRankType("hot");
		rankAllGame2.setGameID("2");
		rankAllGame2.setRankScore(200.0F);
		rankAllGames.add(rankAllGame1);
		rankAllGames.add(rankAllGame2);
		rankMapper.updateAllRankScore(rankAllGames);

	}

	@Test
	void testFindGames(){
//		Game game=findGamesMapper.getGameDetailInfo("6");
//		System.out.println(game.getBackground());
//		List<CommentResponse> commentResponses=findGamesMapper.getGameCommentByGameID("2");
//		System.out.println(commentResponses.get(0).getUsername());
		//AdaptiveRankScore adaptiveRankScore=new AdaptiveRankScore();
		//adaptiveRankScore.adaptiveRankScore();

	}
	@Test
	void testChat() {
		/*List<Message> messages=chatMapper.getAllMessages("4554");
		System.out.println(messages.get(1).getProfile());*/
		RequestMessage requestMessage = new RequestMessage();

	}

}
