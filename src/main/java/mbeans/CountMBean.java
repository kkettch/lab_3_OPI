package mbeans;

public interface CountMBean {
    long getNumberOfAllPoints();
    long getNumberOfFailedPoints();
    boolean isLastNPointsFailed(int n);
}
