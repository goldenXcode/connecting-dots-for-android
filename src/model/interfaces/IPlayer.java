package model.interfaces;

public interface IPlayer {

	String getName();

	void setName(String name);

	String getSign();

	void setSign(String sign);

	void increaseScore(int score);

	int getCurrentScore();

}
