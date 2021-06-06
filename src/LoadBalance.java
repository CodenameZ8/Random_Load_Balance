import java.util.*;

public class LoadBalance {

    //Creating simple random server of 3 variables
    private final List<Integer> server= Arrays.asList(1,2,3);

    //random server running
    public  Integer randomServer() {
        int i = new Random().nextInt(server.size());
        return server.get(i);
    }
    // using server class to create list of objects
    List<Server> weightServer=new ArrayList<>(Arrays.asList(
            new Server(1,6,true),
            new Server(2,3,true),
            new Server(3,2,true)));

    //random server with weight
    public Server randomWeightServer() {

        //if no server is available failover server is used
        if(weightServer.isEmpty()) {
            return new Server(4,1,true);
        }

        int total = getTotalWeight();
        //using random number between 0 to total weight
        int i = new Random().nextInt(total) + 1;
        int accumulated = 0;
        for (Server node : weightServer) {
            accumulated += node.weight;
       //select any server where weight its accumulated weight is more than random number
            if (accumulated >= i) {
                return node;
            }
        }
        return null;
    }

    //setting server to fail by removing from list
    private void setServerFail() {
        if(!weightServer.isEmpty())
        weightServer.remove(0);
    }

    // bring back all the servers
    private  void setAllServerUP() {
        weightServer.clear();
        weightServer.add(new Server(1,6,true));
        weightServer.add(new Server(2,3,true));
        weightServer.add(new Server(3,1,true));
    }

    //getting total weight of all server available
    private int getTotalWeight() {
        int total = 0;
        for (Server node : weightServer) {
            total += node.weight;
        }
        return total;
    }

    // generating random messages to be processed
    private final List<String> phoneCompany=Arrays.asList("Jio","Airtel","VI","BSNL");
    private String phoneMessage(){
        Random random=new Random();
        Collections.shuffle(phoneCompany);
        String generatedString = random.ints(48, 122 + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(10)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return phoneCompany.get(1)+":"+generatedString;
    }

    //printing final result
    public void resultPrint(Map<String, Integer> map)
    {
        int i=0,j=0,k=0,l=0;
        for (String key : map.keySet()) {
            switch(map.get(key)){
                case 1:i++; break;
                case 2:j++; break;
                case 3:k++; break;
                case 4:l++; break;
            }
            //uncomment below statement to get all the messages
            //System.out.println(key+":"+map.get(key));
        }
        System.out.println("Servers used 1:"+i+" 2:"+j+" 3:"+k+" 4:"+l);

    }


    public static void main(String[] args)
    {
        LoadBalance loadbalance=new LoadBalance();
        //number of messages to be sent
       int numberOfMessage=100;

       // random server without weight
        Map<String,Integer> randomOutputMap = new HashMap<>();
        for (int i = 0; i < numberOfMessage; i++) {
            String message=loadbalance.phoneMessage();
            Integer node= loadbalance.randomServer();
            randomOutputMap.put(message,node);
            }

        //random server with defined weights
        Map<String, Integer> randomWeightOutputMap = new HashMap<>();
        try {
            for (int i = 0; i < numberOfMessage; i++) {

                //Failing server on certain numbers
                //failing skews weighted data uncomment to get results for pure weighted random
                if (i % 3 == 0) loadbalance.setServerFail();
                if (i % 5 == 0) loadbalance.setServerFail();
                if (i % 7 == 0) loadbalance.setServerFail();
                //Starting all servers
                if (i % 4 == 0) loadbalance.setAllServerUP();
                //Server to which message is sent
                Server node = loadbalance.randomWeightServer();
                String message = loadbalance.phoneMessage();
                //inserting the server name and message into map
                randomWeightOutputMap.put(message, node.serverNumber);
            }
        } catch(Exception e)

        {
            e.printStackTrace();
        }

        //printing the final result
        //uncomment for result of random load balancer
        //loadbalance.resultPrint(randomOutputMap);
        loadbalance.resultPrint(randomWeightOutputMap);
        }

}



        


