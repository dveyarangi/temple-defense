package yarangi.game.temple.ai.weapons;

public interface IFeedbackCarrier {
	public void setFeedback(IFeedbackBeacon beacon);
	public IFeedbackBeacon getFeedback();
}
