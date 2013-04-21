package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;

public class Main {
 
    public static void main(String[] args) throws IOException {
        String studentNr = "14140105";
        if ( args.length>0) {
            studentNr = args[0];
        }
 
        String url = "http://ohtustats-2013.herokuapp.com/opiskelija/"+studentNr+".json";
 
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(url);
        client.executeMethod(method);
 
        InputStream stream =  method.getResponseBodyAsStream();
 
        String bodyText = IOUtils.toString(stream);
 
//        System.out.println("json-muotoinen data:");
//        System.out.println( bodyText );
 
        Gson mapper = new Gson();
        Palautukset palautukset = mapper.fromJson(bodyText, Palautukset.class);
 
//        System.out.println("oliot:");
        System.out.println("opiskelijanumero " + palautukset.getPalautukset().get(0).getOpiskelijanumero());        

        int thtv = 0;
        int tuntia = 0;
        
        for (Palautus palautus : palautukset.getPalautukset()) {
            System.out.print("viikko " + palautus.getViikko() + ":");
            System.out.print(palautus.getTehtavia() + " tehtävää ");
            System.out.print(palautus.getTehtavat() + "\t");
            System.out.print("aikaa kului " + palautus.getTunteja() + " tuntia");
            System.out.println();
            thtv += palautus.getTehtavia();
            tuntia += palautus.getTunteja();
            //            System.out.println( palautus );
        }
        
        System.out.println("yhteensä " + thtv + " tehtävää " + tuntia + " tuntia");
 
    }
}