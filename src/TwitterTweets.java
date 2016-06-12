
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.TimeZone;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterTweets {

	public static void main(String[] args) throws TwitterException {

		ConfigurationBuilder cf = new ConfigurationBuilder();

		cf.setDebugEnabled(true).setOAuthConsumerKey("")
		.setOAuthConsumerSecret("")
		.setOAuthAccessToken("")
		.setOAuthAccessTokenSecret("");

		TwitterFactory tf = new TwitterFactory(cf.build());
		twitter4j.Twitter twitter = tf.getInstance();
		
		
		int pageno = 1;
	
		/*
		 * Liste für alle Tweets
		 */
		List<String> allTweetsTrump = new ArrayList<String>();
		List<String> allTweetsClinton = new ArrayList<String>();
		String tweetsTrump;
		String tweetsClinton;
		List statuses = new ArrayList();
		
		while (true) {

			  try {

			    int size = allTweetsTrump.size(); 
		
			    /*
			     * Paging page = new Paging(pageno++, 200);
			     * pageno++ --> damit alle seiten durchgelaufen werden bis zum Ende 
			     * 200 --> pro Seite kann Twitter 200 Tweets ausgeben 
			     */
			    Paging page = new Paging(pageno++, 200)/*.sinceId(663520201187590144L)*/;
			    /*
			     * Zugriff auf alle Tweets von Trump
			     */
			    List<Status> firstTweetsTrump = twitter.getUserTimeline("realDonaldTrump", page);
			    /*
			     * Listeninhalt durchlaufen und für alle Tweets nur Namen und Tweet auslesen und in eine neue Liste einfügen
			     */
			    for (Status st : firstTweetsTrump) {
					tweetsTrump = st.getCreatedAt() + "@" + st.getUser().getScreenName() + " - " + st.getText() + st.getId();
					allTweetsTrump.add(tweetsTrump);
				}
			    if (allTweetsTrump.size() == size)
			      break;
			  }
			  catch(TwitterException e) {
			    e.printStackTrace();
			  }
			}
		
		
		/*
		 * Vorlage aus dem Internet gibt alle Tweets aus bis 2009
		 */
//		while (true) {
//
//			  try {
//
//			    int size = statuses.size(); 
//			    Paging page = new Paging(pageno++, 100);
//			    statuses.addAll(twitter.getUserTimeline("realDonaldTrump", page));
//			    if (statuses.size() == size)
//			      break;
//			  }
//			  catch(TwitterException e) {
//
//			    e.printStackTrace();
//			  }
//			}



		System.out.println(allTweetsTrump);

		listInDatei(statuses, new File("texte/trumptweets.txt"));
		listInDatei(allTweetsClinton, new File("texte/clintontweets.txt"));
	}

	/*
	 * Liste mit Tweets von Trump in .txt Datei ausgeben
	 */
	private static void listInDatei(List list, File datei) {

		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(new FileWriter(datei));
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				Object o = iter.next();
				printWriter.println(o);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (printWriter != null)
				printWriter.close();

		}
	}

}
