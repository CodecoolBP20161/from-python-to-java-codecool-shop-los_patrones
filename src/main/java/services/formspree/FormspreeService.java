package services.formspree;

import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

public class FormspreeService {
    public static void main(String[] args) {
        staticFileLocation("/formspree");
        post("/:email", (request, response) -> {
            try{
                Utils.sendMessage(request, request.params(":email"));
                response.redirect("/html/success.html");
            }catch(Exception e){
                e.printStackTrace();
                response.redirect("/html/failure.html");
            }
            return "";
        });

    }
}