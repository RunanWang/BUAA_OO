public class EleA extends BasicElevator {
    public EleA(ReQueue q, ReQueue t) {
        setQueue(q);
        setTotalQueue(t);
        setId('A');
        setTime(400);
        setCapicity(6);
    }
}
