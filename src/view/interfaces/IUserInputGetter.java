package view.interfaces;

public interface IUserInputGetter {

	int displayOptions(String context, String[] options);

	int askForNumber(String question);

	String askForText(String question);

}
