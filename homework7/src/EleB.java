public class EleB extends BasicElevator {
    public EleB(ReQueue q, ReQueue t) {
        setQueue(q);
        setTotalQueue(t);
        setId('B');
        setTime(500);
        setCapicity(8);
    }
}
