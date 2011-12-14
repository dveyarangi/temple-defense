package yarangi.game.harmonium.ai.weapons;

public interface IFeedbackCarrier {
	public void setFeedback(IFeedbackBeacon beacon);
	public IFeedbackBeacon getFeedback();
}
