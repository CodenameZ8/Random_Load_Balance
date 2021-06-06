//Class to model the server for weighted random algorithm
public class Server {
    int serverNumber;
    int weight;
    boolean status;

    public Server() {}

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Server(int serverNumber, int weight, boolean status) {
        this.serverNumber = serverNumber;
        this.weight = weight;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Server{" +
                "serverNumber=" + serverNumber +
                ", weight=" + weight +
                ", status=" + status +
                '}';
    }
}
