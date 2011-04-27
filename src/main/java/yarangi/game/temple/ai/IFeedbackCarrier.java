package yarangi.game.temple.ai;

public interface IFeedbackCarrier {
	public void setFeedback(IFeedbackBeacon beacon);
	public IFeedbackBeacon getFeedback();
}
