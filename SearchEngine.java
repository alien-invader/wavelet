import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class SearchEngine {
    
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.

    List<String> result = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Hi, welcome to the web!");
        } else if (url.getPath().contains("/search")) {
            String temp = "";
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    String substring = parameters[1];
                    
                    for (String eachstring : result) {
                        if(eachstring.contains(substring)){
                            temp += eachstring + " ";
                        }
                    }
                    return "Your search result is " + temp;
                    //num += Integer.parseInt(parameters[1]);
                    //return String.format("Number increased by %s! It's now %d", parameters[1], num);
            }
            return temp;
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    String temp = parameters[1];
                    result.add(temp);
                    return "You added the word: " + temp;
                    //num += Integer.parseInt(parameters[1]);
                    //return String.format("Number increased by %s! It's now %d", parameters[1], num);
                }
            }
            return "404 Not Found!";
        }
    }

}