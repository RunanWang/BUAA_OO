public class EleC extends BasicElevator {
    public EleC(ReQueue q, ReQueue t) {
        setQueue(q);
        setTotalQueue(t);
        setId('C');
        setTime(600);
        setCapicity(7);
    }
}
