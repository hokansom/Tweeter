package edu.byu.cs.tweeter.net;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusGenerator {
    private static final String STATUS_PATH = "C:\\Users\\morga\\Documents\\BYU\\Winter-2020\\CS-340\\Tweeter\\tweeter-1\\app\\src\\main\\res\\Statuses.json";

    private static List<String>  samples = new ArrayList<String>(Arrays.asList("Status 1 @TestUser www.google.com", "Status 2  @TestUser www.facebook.com" , "Status 3 @TestUser", "Status 4  @TestUser", "Status 5 @TestUser"));


    private static StatusGenerator statusGenerator;

    private StatusGenerator() {

    }


    public static StatusGenerator getInstance(){
        if(statusGenerator == null){
            statusGenerator = new StatusGenerator();
        }
        return statusGenerator;
    }


    public List<Status> generateAllStatuses(List<User> users, int minNumOfStatuses, int maxNumOfStatuses){
        List<Status> statuses = new ArrayList<>();
        for(User user: users){
            List<Status> temp = generateUserStatuses(user, minNumOfStatuses, maxNumOfStatuses);
            for(Status status : temp){
                statuses.add(status);
            }
        }
        return statuses;
    }

    private List<Status> generateUserStatuses(@NotNull User user, int minNumOfStatuses, int maxNumOfStatuses){
        List<Status> statuses = new ArrayList<>();

        assert minNumOfStatuses >= 0 : minNumOfStatuses;
        assert maxNumOfStatuses > minNumOfStatuses: maxNumOfStatuses;

        Random random = new Random();
        int numStatusToGenerate = random.nextInt(maxNumOfStatuses - minNumOfStatuses) + minNumOfStatuses;

        for(int i = 0; i < numStatusToGenerate; i++){
            statuses.add(generateStatus(user));
        }

        Collections.sort(statuses, new Comparator<Status>() {
            @Override
            public int compare(Status status1, Status status2) {
                int result = status1.compareTo(status2);
                return result;
            }
        });

        return statuses;
    }

    private Status generateStatus(User user){
        Random random = new Random();

        String message = samples.get(random.nextInt(samples.size()));

        Status status = new Status(user, message);

        return status;
    }

//    private static String [] readInStatuses(){
//        Statuses statuses = null;
//
//        JSONParser jsonParser = new JSONParser();
//
//
//        try (FileReader reader = new FileReader(STATUS_PATH))
//        {
//            //Read JSON file
//            Object obj = jsonParser.parse(reader);
//
//            JSONArray jsonArray = (JSONArray) obj;
//            statuses = (new Gson()).fromJson(jsonArray.toString(), Statuses.class);
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        return statuses == null ? null : statuses.getStatuses();
//    }

//    public List<String> getSampleStatuses(){
//        return samples;
//    }
//
//    class Statuses {
//
//        public Statuses(){
//        }
//        private String [] data;
//        private String [] getStatuses() { return data; }
//    }
}


