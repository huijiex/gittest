package collectingdata;
//import twitter4j.util.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;
import org.json.JSONException;  
import org.json.JSONObject;  
//import twitter4j.*;
//import twitter4j.management.*;
//import twitter4j.api.*;
//import twitter4j.conf.*;
//import twitter4j.json.*;
//import twitter4j.auth.*;


public class SearchTweets {
	private static File file;
	private static FileWriter fw;
	private static BufferedWriter bw;
	/*static double[][] boundingBox= {
			  {
			    144, -38
			  }
			  , {
			    146, -37
			  }
			};*/
	static double[][] boundingBox= {
			  {
			    112, -44
			  }
			  , {
			    154, -10
			  }
			};
	public static void main(String[] args) {
		String path = "e:\\yaner\\res.txt";
		file =new File(path);
        //File file = new File(path);
        if(!file.exists()){
            file.getParentFile().mkdirs();          
        }
        try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			fw = new FileWriter(file, true);
			bw = new BufferedWriter(fw);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			BufferedReader br = new BufferedReader(new FileReader(  
                    "json/tweets.json"));
			String s="";
			String tmpstr = "";
			while ((s = br.readLine()) != null) {
				try {  
					JSONObject dataJson = new JSONObject(s);
					String text= dataJson.get("text").toString();
					SearchTweets.findName(text);
					//String text=textjson.toString();
					tmpstr=tmpstr+text;
			} catch (JSONException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } 
			//SearchTweets.findName(tmpstr);
			}
			}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
        	bw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		 //System.out.println("start");
		    //Twitter twitter = new TwitterFactory().getInstance();
		    /*try {
		    	double lat = -37.4372155;
		    	double lon = 144.7453688;
		    	double res = 100;
		    	String resUnit = "i";
		    	Query query = new Query().geoCode(new GeoLocation(lat,lon), res, resUnit); 
		    	query.count(11); //You can also set the number of tweets to return per page, up to a max of 100
		    	QueryResult result;
		    	do {
	                result = twitter.search(query);
	                List<Status> tweets = result.getTweets();
	                for (Status tweet : tweets) {
	                		System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText()+tweet.getGeoLocation().getLatitude());
	                		System.exit(1);
	                }
	            } while ((query = result.nextQuery()) != null);
	            System.exit(0);
		    	//Place place = twitter.getGeoDetails("melbourne");
		    	//GeoLocation [][] geolocation = place.getBoundingBoxCoordinates();
		    	//System.out.println(""+geolocation[0][0]+geolocation[0][1]+geolocation[1][0]+geolocation[1][1]);
		    }
		    catch (TwitterException te) {
	            te.printStackTrace();
	            System.out.println("Failed to retrieve geo details: " + te.getMessage());
	            System.exit(-1);
	        }*/
		    //TwitterStream twitterStream;
		    //Twitter twitter = new TwitterFactory().getInstance();
	        /*try {
	            Query query = new Query(args[0]);
	            QueryResult result;
	            do {
	                result = twitter.search(query);
	                List<Status> tweets = result.getTweets();
	                for (Status tweet : tweets) {
	                	//if(tweet.getGeoLocation()!=null) {
	                		System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());//+tweet.getGeoLocation().getLatitude());
	                		//System.exit(1);
	                	//}
	                }
	            } while ((query = result.nextQuery()) != null);
	            System.exit(0);
	        } catch (TwitterException te) {
	            te.printStackTrace();
	            System.out.println("Failed to search tweets: " + te.getMessage());
	            System.exit(-1);
	        }*/
		    /*StatusListener listener = new StatusListener() {
		    	  //@Override
		    	  public void onStatus(Status status) {
		    	    GeoLocation loc = status.getGeoLocation();
		    	    Place pla=status.getPlace();
		    	    //if(loc!=null||pla!=null) {
		    	    	System.out.println("@" + status.getUser().getScreenName() + " - " + loc);
		    	    	System.out.println("@" + status.getUser().getScreenName() + " - " + pla);
		    	    	System.out.println("STATUS!!!:"+status);
		    	    //}
		    	  }

		    	  //@Override
		    	  public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		    	    System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
		    	  }

		    	  //@Override
		    	  public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		    	    System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
		    	  }

		    	  //@Override
		    	  public void onScrubGeo(long userId, long upToStatusId) {
		    	    System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
		    	  }

		    	  //@Override
		    	  public void onStallWarning(StallWarning warning) {
		    	    System.out.println("Got stall warning:" + warning);
		    	  }

		    	  //@Override
		    	  public void onException(Exception ex) {
		    	    ex.printStackTrace();
		    	  }
		    };
		    
		    	ConfigurationBuilder cb = new ConfigurationBuilder();
		    	cb.setOAuthConsumerKey("TwRuKCE3rpe8xDy19NaTVwL5g");
		    	cb.setOAuthConsumerSecret("iu5mCl1yfTPaTVpsh1JneyrdrCpTyz8rtLqMpO0j1SQ7KfR3mY");
		    	cb.setOAuthAccessToken("970215325676462080-sLkH8A1U1fi3fuAF8B9r4vqCuQF9nTx");
		    	cb.setOAuthAccessTokenSecret("pq0NqvFWoKLkQwMUkiXcKyyY1gCmj02tUAtz1KNIeJjZV");
		    	TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
		    	twitterStream.addListener(listener);
		    	FilterQuery filter = new FilterQuery();
		    	//filter.locations(boundingBox);
		    	filter.track("happy birthday");
		    	twitterStream.filter(filter);	    */
	}
	public static void findName(String str) throws IOException {
		InputStream modelInToken = null;
		InputStream modelIn = null;
		
		modelInToken =new FileInputStream("nlpbin/da-token.bin");
		TokenizerModel modelToken = new TokenizerModel(modelInToken);
		Tokenizer tokenizer = new TokenizerME(modelToken);
		String tokens[] = tokenizer.tokenize(str);
		
		InputStream modeIn = new FileInputStream("nlpbin/en-ner-location.bin");
		TokenNameFinderModel model = new TokenNameFinderModel(modeIn);
		//modeIn.close();
		NameFinderME nameFinder = new NameFinderME(model);
			Span nameSpans[] = nameFinder.find(tokens);
			double[] spanProbs = nameFinder.probs(nameSpans);
			//System.out.println(spanProbs.length);
			//System.out.println(spanProbs.length);
			for( int i = 0; i<nameSpans.length; i++) {
	    		System.out.println("Span: "+nameSpans[i].toString());
	    		System.out.println("Covered text is: "+tokens[nameSpans[i].getStart()] + " " + tokens[nameSpans[i].getStart()+1]);
	    		System.out.println("Probability is: "+spanProbs[i]);
	    		//FileWriter fw = new FileWriter(file, true);
	            //BufferedWriter bw = new BufferedWriter(fw);
	            bw.write(tokens[nameSpans[i].getStart()]+" "+tokens[nameSpans[i].getStart()+1]+"\r\n");
	            bw.flush();
	            //bw.close();
	            //fw.close();
	    		//System.exit(-1);
	    	}		
			
	}

}
