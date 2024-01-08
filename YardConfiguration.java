
package trainschedulingsimulation;
//config of the tracks and switches
public class YardConfiguration {
    private int inboundTrack;
    private int firstSwitch;
    private int secondSwitch;
    private int thirdSwitch;
    private int outboundTrack;

    //constructor for yard config
    public YardConfiguration(int inboundTrack, int firstSwitch, int secondSwitch, int thirdSwitch, int outboundTrack) {
        this.inboundTrack = inboundTrack;
        this.firstSwitch = firstSwitch;
        this.secondSwitch = secondSwitch;
        this.thirdSwitch = thirdSwitch;
        this.outboundTrack = outboundTrack;
    }

    //getter methods to retrieve certsain info
    public int getInboundTrack() {
        return inboundTrack;
    }

    public int getFirstSwitch() {
        return firstSwitch;
    }

    public int getSecondSwitch() {
        return secondSwitch;
    }

    public int getThirdSwitch() {
        return thirdSwitch;
    }

    public int getOutboundTrack() {
        return outboundTrack;
    }
}
